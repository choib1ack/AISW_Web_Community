import React, {useState} from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import PersonImage from "../image/person.svg"
import {useDispatch, useSelector} from "react-redux";
import './MyPage.css';
import {logout} from "../features/userSlice";

export default function MyPage(props) {
    // redux toolkit
    const user = useSelector(state => state.user)
    const dispatch = useDispatch()

    return (
        <div>
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
                                    }}>{user.userData.college === "IT_CONVERGENCE" ? "IT융합대학" : "??대학"} {user.userData.department === "SOFTWARE" ? "소프트웨어학과" : "??과"} {user.userData.student_id}</p>
                                </div>
                            </Col>
                            <Col xs={3} md={3}
                                 style={{textAlign: 'center', fontSize: '12px', color: '#8C8C8C'}}>
                                <p style={{cursor: 'pointer'}} onClick={()=> dispatch(logout())}>로그아웃</p>
                            </Col>
                        </Row>
                        <p style={{color: '#6CBACB', margin: '5px'}}>알림</p>
                        <div style={{
                            height: '200px',
                            backgroundColor: '#EFF7F9',
                            border: '1px solid #E3E3E3',
                            overflow: 'auto',
                            borderRadius: '10px',
                            marginBottom: '10px'
                        }}>
                            {makeAlertList()}
                        </div>
                    </Container>
                </Modal.Body>
            </Modal>
        </div>
    );
}

function makeAlertList(props) {
    let style = {
        borderRadius: '10px',
        backgroundColor: '#FFFFFF',
        border: '1px solid #E3E3E3',
        margin: '10px 30px 10px 30px',
        padding: '15px',
        height: '70px'
    }
    // let data = props.data;
    let data = [
        {
            location: '자유게시판',
            contents: '새로운 답글을 확인해보세요 : 소프트웨어학과 커리큘럼...',
            datetime: '3분전'
        },
        {
            location: '과목별게시판',
            contents: '새로운 대댓글을 확인해보세요 : 감사함다',
            datetime: '10분전'
        },
        {
            location: '자유게시판',
            contents: '누군가가 좋아요를 눌렀습니다',
            datetime: '1일전'
        }
    ]
    let lists = [];
    for (let i = 0; i < Object.keys(data).length; i++) {
        lists.push(
            <div key={i} style={style}>
                <p style={{float: 'right', fontSize: '11px', color: '#8C8C8C'}}>{data[i].datetime}</p>
                <p style={{fontSize: '12px', marginBottom: '5px'}}>{data[i].location}</p>
                <p style={{fontSize: '11px', margin: 'none', color: '#8C8C8C'}}>
                    {data[i].contents}
                </p>
            </div>
        );
    }
    return lists;
}
