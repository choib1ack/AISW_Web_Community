import React, {useEffect, useState} from 'react';
import './Menu.css';
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Grid from "@material-ui/core/Grid";
import {Link, useHistory} from "react-router-dom";
import logo from "../image/logo3.png";
import {useDispatch, useSelector} from "react-redux";
import MyPage from "./MyPage";
import GoogleLogin from "react-google-login";
import {setActiveTab} from "../features/menuSlice";
import {GOOGLE_CLIENT_ID, GOOGLE_REDIRECT_URI} from "../constants";
import axios from "axios";

export default function Menu() {
    const [accessToken, setAccessToken] = useState(window.localStorage.getItem("ACCESS_TOKEN") || null);
    const [modalShow, setModalShow] = useState(false);
    const [result, setResult] = useState(null);

    const active_menu = useSelector(state => state.menu);
    const dispatch = useDispatch();

    const history = useHistory();

    const handleJoinFailure = (result) => console.log(result);
    const handleLoginFailure = (result) => console.log(result);

    const handleClickTab = (event) => {
        let name = event.target.name;
        switch (name) {
            case "logo":
                dispatch(setActiveTab(0));
                break;
            case "notice":
                dispatch(setActiveTab(1));
                break;
            case "board":
                dispatch(setActiveTab(2));
                break;
            case "dept_info":
                dispatch(setActiveTab(3));
                break;
            case "site":
                dispatch(setActiveTab(4));
                break;
            case "faq":
                dispatch(setActiveTab(5));
                break;
        }
    }

    // 구글 연동 회원가입 성공시
    function handleJoinSuccess(result) {
        const username = result.tokenObj.idpId + '_' + result.profileObj.googleId;
        const email = result.profileObj.email;
        setResult(result);

        checkExist(username, email, 'join');
    }

    // 구글 연동 로그인 성공시
    async function handleLoginSuccess(result) {
        const username = result.tokenObj.idpId + '_' + result.profileObj.googleId;
        const email = result.profileObj.email;

        checkExist(username, email, 'login');
    }

    function checkExist(username, email, mode) {
        axios.post(`/user/verification`, {
            data: {
                'username': username,
                'email': email
            }
        }).then((res) => {
            if (mode === 'join') {
                moveJoin(res.data.data);
            } else if (mode === 'login') {
                moveLogin(res.data.data, username);
            }
        }).catch(error => error);
    }

    function moveJoin(exist) {
        if (exist.validation === true) {
            alert("이미 가입된 회원입니다.");
        } else {
            const roll = exist.account === 'general' ? 'GENERAL' : 'STUDENT';
            history.push({pathname: '/join', state: {google_data: result, account_role: roll}});
        }
    }

    function moveLogin(exist, username) {
        if (exist.validation === false) {
            alert("회원가입이 필요합니다.");
        } else {
            login(username);
        }
    }

    function login(username) {
        axios.post(`/login`, {
            'username': username,
            'password': 'AISW',
        }).then((res) => {
            window.localStorage.setItem("ACCESS_TOKEN", res.headers.authorization);
            window.localStorage.setItem("REFRESH_TOKEN", res.headers.refresh_token);

            setAccessToken(window.localStorage.getItem("ACCESS_TOKEN"));
        }).catch(error => error);
    }

    return (
        <div className="Menu">
            <Grid>
                <Row style={{borderBottom: 'solid 1px #d0d0d0', padding: '15px'}}>
                    <Col xs={3}>
                        <Link to="/">
                            <img src={logo} style={{width: "120px"}} name="logo" onClick={handleClickTab} alt='...'/>
                        </Link>
                    </Col>
                    <Col xs={6}>
                        <Link to="/notice">
                            <button className="Menu-button" name="notice" onClick={handleClickTab}
                                    style={{color: active_menu.active === 1 ? "#0472FD" : "dimgrey"}}>
                                공지사항
                            </button>
                        </Link>
                        <Link to="/board">
                            <button className="Menu-button" name="board" onClick={handleClickTab}
                                    style={{color: active_menu.active === 2 ? "#0472FD" : "dimgrey"}}>
                                게시판
                            </button>
                        </Link>
                        <Link to="/deptInfo">
                            <button className="Menu-button" name="dept_info" onClick={handleClickTab}
                                    style={{color: active_menu.active === 3 ? "#0472FD" : "dimgrey"}}>
                                학과정보
                            </button>
                        </Link>

                        <Link to="/goodInfo">
                            <button className="Menu-button" name="site" onClick={handleClickTab}
                                    style={{color: active_menu.active === 4 ? "#0472FD" : "dimgrey"}}>
                                유용한사이트
                            </button>
                        </Link>

                        <Link to="/faq">
                            <button className="Menu-button" name="faq" onClick={handleClickTab}
                                    style={{color: active_menu.active === 5 ? "#0472FD" : "dimgrey"}}>
                                FAQ
                            </button>
                        </Link>
                    </Col>

                    {
                        (accessToken) ?
                            (
                                <>
                                    <Col xs={3}>
                                        <button className="Menu-button" onClick={() => setModalShow(true)}>
                                            {accessToken}
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
                                        clientId={GOOGLE_CLIENT_ID}
                                        render={renderProps => (
                                            <button className="Menu-button" onClick={renderProps.onClick}
                                                    disabled={renderProps.disabled}>로그인</button>
                                        )}
                                        onSuccess={result => handleLoginSuccess(result)}
                                        onFailure={result => handleLoginFailure(result)}
                                        redirectUri={GOOGLE_REDIRECT_URI}
                                        cookiePolicy={'single_host_origin'}
                                        // uxMode='redirect'
                                    />
                                    <GoogleLogin
                                        clientId={GOOGLE_CLIENT_ID}
                                        render={renderProps => (
                                            <button className="Menu-button blue-button" onClick={renderProps.onClick}
                                                    disabled={renderProps.disabled}>회원가입</button>
                                        )}
                                        onSuccess={result => handleJoinSuccess(result)}
                                        onFailure={result => handleJoinFailure(result)}
                                        redirectUri={GOOGLE_REDIRECT_URI}
                                        cookiePolicy={'single_host_origin'}
                                        // uxMode='redirect'
                                    />
                                </Col>
                            )
                    }
                </Row>
            </Grid>
        </div>
    );
}
