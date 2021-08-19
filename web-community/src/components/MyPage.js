import React, {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import PersonImage from "../image/person.svg"
import './MyPage.css';
import {useHistory} from "react-router-dom";
import Loading from "./Loading";
import axiosApi from "../axiosApi";
import newIcon from "../icon/new_icon.png"
import moreIcon from "../icon/more_icon.png"
import * as jwt from "jwt-simple";

export default function MyPage(props) {
    const [accessToken, setAccessToken] = useState(window.localStorage.getItem("ACCESS_TOKEN") || null);
    const [userName, setUserName] = useState(null);
    const [department, setDepartment] = useState( null);

    const history = useHistory();

    const [show, setShow] = useState(false);

    useEffect(() => {
        if (accessToken) {
            let decoded = jwt.decode(accessToken.split(' ')[1], 'AISW', false, 'HS512');

            setUserName(decoded.name);
            setDepartment(decoded.department);
        }
    }, [accessToken]);

    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    const handleLogout = () => {
        setShow(false);

        window.localStorage.clear();

        history.push('/')   // 홈으로 가기
    }

    return (
        <div>
            <>
                <Modal show={show} onHide={handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>로그아웃</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>정말로 로그아웃 하시겠습니까 ?</Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleClose}>
                            아니오
                        </Button>
                        <Button variant="primary" onClick={handleLogout}>
                            네
                        </Button>
                    </Modal.Footer>
                </Modal>
            </>

            <Modal {...props} aria-labelledby="contained-modal-title-vcenter" bsPrefix="MyPage">
                <Modal.Header closeButton style={{border: 'none'}}>
                    <Modal.Title
                        id="contained-modal-title-vcenter"
                        style={{fontSize: '16px'}}
                    >
                        내 정보
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body className="show-grid" style={{padding: "0px"}}>
                    <Container style={{padding: 'none'}}>

                        <Row style={{margin: "20px 30px"}}>
                            <Col xs={1} md={1}>
                                <img src={PersonImage}/>
                            </Col>
                            <Col xs={8} md={8}>

                                <div style={{marginLeft: "10px"}}>
                                    <p style={{fontSize: '14px', marginBottom: "0px"}}>{userName}</p>
                                    <p style={{
                                        fontSize: '12px',
                                        color: '#8C8C8C'
                                    }}> {department}</p>
                                </div>
                            </Col>
                            <Col xs={3} md={3}
                                 style={{textAlign: 'center', fontSize: '12px', color: '#8C8C8C'}}>
                                <p style={{cursor: 'pointer'}} onClick={handleShow}>로그아웃</p>
                            </Col>
                        </Row>
                        <p style={{color: '#0472FD', margin: '5px'}}>알림</p>
                        <div style={{
                            height: '200px',
                            backgroundColor: '#e7f1ff',
                            border: '1px solid #E3E3E3',
                            overflow: 'auto',
                            borderRadius: '10px',
                            marginBottom: '10px'
                        }}>
                            <MakeAlertList
                                history={history}
                            />
                        </div>
                    </Container>
                </Modal.Body>
            </Modal>
        </div>
    );
}

function MakeAlertList({history}) {

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [alertData, setAlertData] = useState(
        {
            data: null,
            page_info: null
        });

    useEffect(() => {
        const fetchMyPageData = async () => {
            try {
                setLoading(true);
                setError(null);

                await axiosApi.get("/auth/alert")
                    .then(res => {
                            setAlertData({
                                data: res.data.data,
                                page_info: res.data.pagination
                            });
                            console.log(res);
                        }
                    );

            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchMyPageData();
    }, []);

    if (loading) return <Loading/>;
    if (error) return <div>에러가 발생했습니다{error.toString()}</div>;
    if (!alertData.data) return <div>데이터가 없습니다.</div>;

    let style = {
        borderRadius: '10px',
        backgroundColor: '#FFFFFF',
        border: '1px solid #E3E3E3',
        margin: '10px 30px 10px 30px',
        padding: '15px',
        height: '70px',
        cursor: 'pointer'
    }
    let style_viewed = {
        borderRadius: '10px',
        backgroundColor: '#FFFFFF',
        border: '1px solid #E3E3E3',
        margin: '10px 30px 10px 30px',
        padding: '15px',
        height: '70px',
        cursor: 'pointer',
        opacity: '0.6'
    }

    const returnBoardName = (category) => {
        switch (category) {
            case "FREE":
                return "자유게시판";
            case "QNA":
                return "과목별게시판";
            case "JOB":
                return "취업게시판";
        }
    }

    const returnAlertType = (alert_category) => {
        switch (alert_category) {
            case "COMMENT":
                return "새로운 댓글이 등록되었습니다.";
            case "NESTED_COMMENT":
                return "새로운 대댓글이 등록되었습니다";
            case "LIKE_POST":
                return "누군가 게시물에 좋아요를 눌렀습니다.";
            case "LIKE_COMMENT":
                return "누군가 댓글에 좋아요를 눌렀습니다.";
        }
    }

    // 출처 : https://kdinner.tistory.com/68
    const timeExpression = (value) => {
        const now = new Date();
        const timeValue = new Date(value);

        const betweenTime = Math.floor((now.getTime() - timeValue.getTime()) / 1000 / 60);
        if (betweenTime < 1) return '방금';
        if (betweenTime < 60) {
            return `${betweenTime}분 전`;
        }

        const betweenTimeHour = Math.floor(betweenTime / 60);
        if (betweenTimeHour < 24) {
            return `${betweenTimeHour}시간 전`;
        }

        const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
        if (betweenTimeDay < 365) {
            return `${betweenTimeDay}일 전`;
        }

        return `${Math.floor(betweenTimeDay / 365)}년 전`;
    }

    const ToLink = async (data) => {
        history.push(`/board/${data.second_category.toLowerCase()}/${data.post_id}`);
        await axiosApi.get("/auth/alert/" + data.id);
    }


    return (
        <>
            {alertData.data.map((data, index) => (
                <div key={index} style={!data.checked ? style : style_viewed}
                     onClick={() => ToLink(data)}
                >
                    <p style={{
                        float: 'right',
                        fontSize: '11px',
                        color: '#8C8C8C'
                    }}>{timeExpression(data.created_at)}</p>
                    <p style={{fontSize: '12px', marginBottom: '5px'}}>{returnBoardName(data.second_category)}
                        {data.checked ? null :
                            <img src={newIcon} style={{width: "12px", height: "12px", marginLeft: "5px"}}/>}</p>
                    <p style={{fontSize: '11px', margin: 'none', color: '#8C8C8C'}}>
                        {returnAlertType(data.alert_category)}
                    </p>
                </div>
            ))}
        </>
    );
}
