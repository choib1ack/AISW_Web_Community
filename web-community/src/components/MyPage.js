import React, {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import PersonImage from "../image/person.svg"
import {useDispatch, useSelector} from "react-redux";
import './MyPage.css';
import {logout} from "../features/userSlice";
import {useHistory} from "react-router-dom";
import Loading from "./Loading";
import axiosApi from "../axiosApi";

export default function MyPage(props) {
    const history = useHistory();

    // redux toolkit
    const user = useSelector(state => state.user)
    const dispatch = useDispatch()

    const [show, setShow] = useState(false);

    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    const handleLogout = () => {
        setShow(false);

        window.localStorage.clear();

        dispatch(logout())
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
                                    <p style={{fontSize: '14px', marginBottom: "0px"}}>{user.userData.name}</p>
                                    <p style={{
                                        fontSize: '12px',
                                        color: '#8C8C8C'
                                    }}>{user.userData.college_name === "IT_CONVERGENCE" ? "IT융합대학" : "??대학"} {user.userData.department_name === "SOFTWARE" ? "소프트웨어학과" : "??과"} {user.userData.student_id}</p>
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
    const [alertData, setAlertData] = useState(null);

    useEffect(() => {
        const fetchMyPageData = async () => {

            try {

                setLoading(true);
                setError(null);

                await axiosApi.get("/auth/alert")
                    .then(res =>{
                        setAlertData(res.data.data);
                            console.log(res.data.data);
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
    if (!alertData) return <div>데이터가 없습니다.</div>;

    let style = {
        borderRadius: '10px',
        backgroundColor: '#FFFFFF',
        border: '1px solid #E3E3E3',
        margin: '10px 30px 10px 30px',
        padding: '15px',
        height: '70px'
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

    const ToLink = (url) => {
        history.push(url);
    }


    return (
        <>
            {alertData.map((data, index)=>(
                <div key={index} style={style}
                     onClick={() => ToLink(`/board/${data.second_category.toLowerCase()}/${data.id}`)}
                >
                    <p style={{float: 'right', fontSize: '11px', color: '#8C8C8C'}}>{timeExpression(data.created_at)}</p>
                    <p style={{fontSize: '12px', marginBottom: '5px'}}>{returnBoardName(data.second_category)}</p>
                    <p style={{fontSize: '11px', margin: 'none', color: '#8C8C8C'}}>
                        {returnAlertType(data.alert_category)}
                    </p>
                </div>
            ))}
        </>
    );
}
