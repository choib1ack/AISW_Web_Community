import React, {useEffect, useState} from 'react';
import './Menu.css';
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Grid from "@material-ui/core/Grid";
import {Link, useHistory} from "react-router-dom";
import logo from "../image/logo3.png";
import {useDispatch, useSelector} from "react-redux";
import MyPage from "./MyPage";
import Button from "react-bootstrap/Button";
import GoogleLogin from "react-google-login";
import {setOnline, logout, login, join} from "../features/userSlice";
import axios from "axios";

export default function Menu() {
    const history = useHistory();

    const [modalShow, setModalShow] = useState(false);
    const [userName, setUserName] = useState(() => JSON.parse(window.localStorage.getItem("user_name")) || null);

    // 이미 있는 회원인지 확인
    async function isExistUser(username, email) {
        let result = {validation: null, account: null};

        await axios.post(`/user/verification`, {
            headers: {
                "Content-Type": `application/json`
            },
            data: {
                username: username,
                email: email
            }
        }).then((res) => {
            result.validation = res.data.data.validation === true;
            result.account = res.data.data.account;
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log("에러 발생", errorObject);
        })

        return result;
    }

    // 구글 연동 성공시
    async function handleLoginSuccess(result) {
        const username = result.tokenObj.idpId + '_' + result.profileObj.googleId;

        await axios.post(`/login`, {
            username: username,
            password: 'AISW',
        }).then((res) => {
            console.log(res);
            window.localStorage.setItem("auth", JSON.stringify(res.headers.authorization)); // 토큰 저장
            window.localStorage.setItem("user_name", JSON.stringify(result.profileObj.familyName)); // 유저 이름 저장

            history.push('/')   // 홈으로 가기
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log("에러 발생", errorObject);
        })
    }

    // 구글 연동 실패시
    const handleLoginFailure = (result) => {
        console.log("구글 연동 실패", result)
    }

    // 구글 연동 회원가입 성공시
    async function handleJoinSuccess(result) {
        const username = result.tokenObj.idpId + '_' + result.profileObj.googleId;
        const email = result.profileObj.email;
        const isExist = await isExistUser(username, email);

        if (isExist.validation === true) {
            alert("이미 가입된 회원입니다.")
        } else {
            let roll = null;
            if (isExist[1] === 'gachon') {
                roll = 'STUDENT';
            } else {
                roll = 'GENERAL';
            }
            history.push({pathname: '/join', state: {google_data: result, account_role: roll}})
        }
    }

    // 구글 연동 회원가입 실패시
    const handleJoinFailure = (result) => {
        console.log("구글 연동 실패", result)
    }

    return (
        <div className="Menu">
            <Grid>
                <Row style={{borderBottom: 'solid 1px #d0d0d0', padding: '15px'}}>
                    <Col xs={3}>
                        <Link to="/">
                            <img src={logo} style={{width: "120px"}}/>
                        </Link>
                    </Col>
                    <Col xs={6}>
                        <Link to="/notice">
                            <button className="Menu-button">
                                공지사항
                            </button>
                        </Link>
                        <Link to="/board">
                            <button className="Menu-button">
                                게시판
                            </button>
                        </Link>
                        <Link to="/deptInfo">
                            <button className="Menu-button">
                                학과정보
                            </button>
                        </Link>

                        {/*<Link to="/jobInfo">*/}
                        {/*    <button className="Menu-button">*/}
                        {/*        채용정보*/}
                        {/*    </button>*/}
                        {/*</Link>*/}
                        {/*<Link to="/contestInfo">*/}
                        {/*    <button className="Menu-button">*/}
                        {/*        공모전/대외활동*/}
                        {/*    </button>*/}
                        {/*</Link>*/}
                        <Link to="/goodInfo">
                            <button className="Menu-button">
                                유용한사이트
                            </button>
                        </Link>

                        <Link to="/faq">
                            <button className="Menu-button">
                                FAQ
                            </button>
                        </Link>
                    </Col>

                    {
                        (userName != null) ?
                            (
                                <>
                                    <Col xs={3}>
                                        <button className="Menu-button" onClick={() => setModalShow(true)}>
                                            {userName}
                                        </button>
                                        <Link to="/manager">
                                            <button className="Menu-button">
                                                관리자페이지
                                            </button>
                                        </Link>
                                    </Col>

                                    <MyPage show={modalShow} onHide={() => setModalShow(false)}/>
                                </>
                            ) : (
                                <Col xs={3}>
                                    <GoogleLogin
                                        clientId='1051028847648-3edseaslg7hqbrgo5q2thhdag9k6q10e.apps.googleusercontent.com'
                                        render={renderProps => (
                                            <button className="Menu-button" onClick={renderProps.onClick}
                                                    disabled={renderProps.disabled}>로그인</button>
                                        )}
                                        onSuccess={result => {
                                            handleLoginSuccess(result)
                                        }}
                                        onFailure={result => {
                                            handleLoginFailure(result)
                                        }}
                                        // uxMode='redirect'
                                        redirectUri="http://localhost:8080/auth/google/callback"
                                        cookiePolicy={'single_host_origin'}
                                        // responseType='code'
                                    />
                                    <GoogleLogin
                                        clientId='1051028847648-3edseaslg7hqbrgo5q2thhdag9k6q10e.apps.googleusercontent.com'
                                        render={renderProps => (
                                            <button className="Menu-button blue-button" onClick={renderProps.onClick}
                                                    disabled={renderProps.disabled}>회원가입</button>
                                        )}
                                        onSuccess={result => {
                                            handleJoinSuccess(result)
                                        }}
                                        onFailure={result => {
                                            handleJoinFailure(result)
                                        }}
                                        // uxMode='redirect'
                                        redirectUri="http://localhost:8080/auth/google/callback"
                                        cookiePolicy={'single_host_origin'}
                                    />
                                </Col>
                            )
                    }
                </Row>
            </Grid>
        </div>
    );
}
