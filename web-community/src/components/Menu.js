import React, {useEffect, useRef, useState} from 'react';
import './Menu.css';
import {Link, useHistory} from "react-router-dom";
import logo from "../image/logo3.png";
import {useDispatch, useSelector} from "react-redux";
import MyPage from "./User/MyPage";
import GoogleLogin from "react-google-login";
import {setActiveTab} from "../features/menuSlice";
import {ADMIN_ROLE, API_SERVER_URI, GOOGLE_CLIENT_ID, GOOGLE_REDIRECT_URI, LOGIN_SERVER_URI} from "../constants";
import axios from "axios";
import * as jwt from "jwt-simple";
import {setImageUrl, setDecoded} from "../features/userSlice";
import Badge from 'react-bootstrap/Badge'
import {useMediaQuery} from "react-responsive";
import Hamburger from 'hamburger-react';
import Row from "react-bootstrap/Row";


export default function Menu() {
    const isTabletOrMobile = useMediaQuery({query: "(max-width: 767px)"});
    const [accessToken, setAccessToken] = useState(window.localStorage.getItem('ACCESS_TOKEN') || null);
    const [modalShow, setModalShow] = useState(false);
    const [isOpen, setOpen] = useState(false);

    const user = useSelector(state => state.user);
    const menu = useSelector(state => state.menu);
    const dispatch = useDispatch();

    const history = useHistory();

    const handleJoinFailure = (result) => console.log(result);
    const handleLoginFailure = (result) => console.log(result);
    const handleModalShow = () => setModalShow(!modalShow);

    const decodingAccessToken = (accessToken) => {
        try {
            if (accessToken) {
                let decoded = jwt.decode(accessToken.split(' ')[1], 'AISW', false, 'HS512');
                dispatch(setDecoded(decoded));
                history.push('/');
            }
        } catch (e) {
            console.log(e);
        }
    }

    // 구글 연동 회원가입 성공시
    async function handleJoinSuccess(result) {
        const username = result.tokenObj.idpId + '_' + result.profileObj.googleId;
        const email = result.profileObj.email;

        await checkExist(username, email)
            .then(res => {
                moveJoin(res.data.data, result);
            })
            .catch(() => alert("회원가입에 실패하였습니다."));
    }

    // 구글 연동 로그인 성공시
    async function handleLoginSuccess(result) {
        const username = result.tokenObj.idpId + '_' + result.profileObj.googleId;
        const email = result.profileObj.email;

        dispatch(setImageUrl(result.profileObj.imageUrl));

        await checkExist(username, email)
            .then(res => {
                moveLogin(res.data.data, username);
            }).catch(() => alert("로그인에 실패하였습니다."));
    }

    // 존재하는 회원인지 확인
    function checkExist(username, email) {
        return axios.post(`/user/verification`, {
            data: {
                'username': username,
                'email': email
            }
        });
    }

    function moveJoin(exist, result) {
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
        const instance = axios.create({
            baseURL: LOGIN_SERVER_URI
        });

        instance.post(`/login`, {
            'username': username,
            'password': 'AISW',
        }).then((res) => {
            window.localStorage.setItem("ACCESS_TOKEN", res.headers.authorization);
            window.localStorage.setItem("REFRESH_TOKEN", res.headers.refresh_token);

            decodingAccessToken(res.headers.authorization);
        }).catch(error => error);
    }

    useEffect(() => {
        if (!isTabletOrMobile) {
            setOpen(false);
        }
    }, [isTabletOrMobile]);

    return (
        <div>
            <div className="Menu p-lg-3 p-sm-2">
                <div className="align-self-center">
                    <Link to="/">
                        <img src={logo} style={{width: "120px"}} name="logo" alt='...'/>
                    </Link>
                </div>

                {isTabletOrMobile ?
                    <Row>
                        {(accessToken && user.decoded) ?
                            (
                                <>
                                    <div className="align-self-center">
                                        <div>
                                            <button className="Menu-button" onClick={handleModalShow}
                                                    style={{overflow: 'visible'}}>
                                                <img className="align-self-center" width={22} src={user.imageUrl}
                                                     style={{borderRadius: 50, marginRight: 10}}
                                                     alt="..."/>
                                                {user.decoded.name}
                                                <Badge variant="primary" pill style={{
                                                    padding: "5px",
                                                    transform: 'translate(0px, -10px)'
                                                }}>{menu.unread_alert}</Badge>
                                            </button>
                                        </div>

                                        {modalShow ? <MyPage
                                            myPageShow={modalShow}
                                            setMyPageShow={handleModalShow}
                                        /> : null}
                                    </div>
                                </>
                            ) : null}
                        <div style={{marginRight: 20}}>
                            <Hamburger toggled={isOpen} toggle={setOpen} color="dimgrey" size={20} rounded/>
                        </div>
                    </Row>
                    :
                    <>
                        <div className="align-self-center">
                            <Link to="/notice">
                                <button className={menu.active === 1?"Menu-button-active":"Menu-button"}
                                        name="notice">
                                    공지사항
                                </button>
                            </Link>
                            <Link to="/board">
                                <button className={menu.active === 2?"Menu-button-active":"Menu-button"}
                                        name="board">
                                    게시판
                                </button>
                            </Link>
                            <Link to="/deptInfo">
                                <button className={menu.active === 3?"Menu-button-active":"Menu-button"}
                                        name="dept_info">
                                    학과정보
                                </button>
                            </Link>
                            <Link to="/goodInfo">
                                <button className={menu.active === 4?"Menu-button-active":"Menu-button"}
                                        name="site">
                                    유용한사이트
                                </button>
                            </Link>
                            <Link to="/faq">
                                <button className={menu.active === 5?"Menu-button-active":"Menu-button"}
                                        name="faq">
                                    FAQ
                                </button>
                            </Link>
                        </div>

                        {
                            (accessToken && user.decoded) ?
                                (
                                    <div className="align-self-center">
                                        <button className="Menu-button" onClick={handleModalShow}
                                                style={{overflow: 'visible'}}>
                                            <img className="align-self-center" width={22} src={user.imageUrl}
                                                 style={{borderRadius: 50, marginRight: 10}}
                                                 alt="..."/>
                                            {user.decoded.name}
                                            {menu.unread_alert>0?
                                            <Badge variant="primary" pill style={{
                                                padding: "5px",
                                                transform: 'translate(0px, -10px)'
                                            }}>{menu.unread_alert}</Badge>:null}
                                        </button>
                                        {
                                            ADMIN_ROLE.includes(user.decoded.role) ?
                                                <Link to="/manager">
                                                    <button className={menu.active === 6?"Menu-button-active":"Menu-button"}
                                                            name="manage_page">
                                                        관리자페이지
                                                    </button>
                                                </Link>
                                                :
                                                null
                                        }

                                        {modalShow ? <MyPage
                                            myPageShow={modalShow}
                                            setMyPageShow={handleModalShow}
                                        /> : null}
                                    </div>
                                ) : (
                                    <div className="align-self-center">
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
                                    </div>
                                )
                        }
                    </>
                }
            </div>

            {
                isOpen && isTabletOrMobile && (
                    <div>
                        <div>
                            <Link to="/notice">
                                <button className={menu.active === 1?"Menu-button-active":"Menu-button"} name="notice">
                                    공지사항
                                </button>
                            </Link>
                        </div>
                        <div>
                            <Link to="/board">
                                <button className={menu.active === 2?"Menu-button-active":"Menu-button"} name="board">
                                    게시판
                                </button>
                            </Link>
                        </div>
                        <div>
                            <Link to="/deptInfo">
                                <button className={menu.active === 3?"Menu-button-active":"Menu-button"} name="dept_info">
                                    학과정보
                                </button>
                            </Link>
                        </div>
                        <div>
                            <Link to="/goodInfo">
                                <button className={menu.active === 4?"Menu-button-active":"Menu-button"} name="site">
                                    유용한사이트
                                </button>
                            </Link>
                        </div>
                        <div>
                            <Link to="/faq">
                                <button className={menu.active === 5?"Menu-button-active":"Menu-button"} name="faq">
                                    FAQ
                                </button>
                            </Link>
                        </div>

                        {
                            (accessToken && user.decoded) ?
                                (
                                    <div className="align-self-center">
                                        {
                                            ADMIN_ROLE.includes(user.decoded.role) ?
                                                <div>
                                                    <Link to="/manager">
                                                        <button className={menu.active === 6?"Menu-button-active":"Menu-button"} name="manage_page">
                                                            관리자페이지
                                                        </button>
                                                    </Link>
                                                </div>
                                                :
                                                null
                                        }
                                    </div>
                                ) : (
                                    <div className="align-self-center">
                                        <div>
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
                                        </div>
                                        <div>
                                            <GoogleLogin
                                                clientId={GOOGLE_CLIENT_ID}
                                                render={renderProps => (
                                                    <button className="Menu-button"
                                                            onClick={renderProps.onClick}
                                                            disabled={renderProps.disabled}>회원가입</button>
                                                )}
                                                onSuccess={result => handleJoinSuccess(result)}
                                                onFailure={result => handleJoinFailure(result)}
                                                redirectUri={GOOGLE_REDIRECT_URI}
                                                cookiePolicy={'single_host_origin'}
                                                // uxMode='redirect'
                                            />
                                        </div>
                                    </div>
                                )
                        }
                    </div>
                )
            }
            <hr className="mt-0"/>
        </div>
    );
}
