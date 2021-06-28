import React, {useState} from 'react';
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

export default function Menu() {
    const history = useHistory();

    // redux toolkit
    const user = useSelector(state => state.user)
    const dispatch = useDispatch()

    const [modalShow, setModalShow] = useState(false);

    // 이미 있는 회원인지 확인
    const isExistUser = () => {

    }

    // 구글 연동 성공시
    const handleLoginSuccess = (result) => {
        console.log("로그인 성공", result)

        history.push('/')
    }

    // 구글 연동 실패시
    const handleLoginFailure = (result) => {
        console.log("로그인 실패", result)
    }

    // 구글 연동 성공시
    const handleJoinSuccess = (result) => {
        console.log("회원가입 성공", result)

        history.push({pathname: '/join', state: {google_data: result}})
    }

    // 구글 연동 실패시
    const handleJoinFailure = (result) => {
        console.log("회원가입 실패", result)
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

                        <Link to="/jobInfo">
                            <button className="Menu-button">
                                채용정보
                            </button>
                        </Link>
                        <Link to="/contestInfo">
                            <button className="Menu-button">
                                공모전/대외활동
                            </button>
                        </Link>
                        <Link to="/banner">
                            <button className="Menu-button font-weight-light">
                                배너 관리
                            </button>
                        </Link>
                        <Link to="/join">
                            <button className="Menu-button font-weight-light">
                                회원 관리
                            </button>
                        </Link>
                    </Col>
                    {
                        (user.isOnline && user.userData != null) ? (
                            <>
                                <Col xs={3}>
                                    <button className="Menu-button" onClick={() => setModalShow(true)}>
                                        {user.userData.name}
                                    </button>
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
                                    onSuccess={result => {handleLoginSuccess(result)}}
                                    onFailure={result => {handleLoginFailure(result)}}
                                    cookiePolicy={'single_host_origin'}
                                />

                                <GoogleLogin
                                    clientId='1051028847648-3edseaslg7hqbrgo5q2thhdag9k6q10e.apps.googleusercontent.com'
                                    render={renderProps => (
                                        <button className="Menu-button blue-button" onClick={renderProps.onClick}
                                                disabled={renderProps.disabled}>회원가입</button>
                                    )}
                                    onSuccess={result => {handleJoinSuccess(result)}}
                                    onFailure={result => {handleJoinFailure(result)}}
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
