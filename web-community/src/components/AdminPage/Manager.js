import React from "react";
import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {useHistory} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {setActiveTab} from "../../features/menuSlice";

function Manager({match}) {

    let history = useHistory();
    const ToLink = (url) => {
        history.push(url);
    }

    const active_change_dispatch = useDispatch();
    active_change_dispatch(setActiveTab(6));

    const user = useSelector(state => state.user);

    if (!(user.decoded.role === "ROLE_ADMIN" || user.decoded.role === "ROLE_DEVELOPER")){
        if (!alert('접근 권한이 없습니다!')) {
            window.history.back();
        }
        return(<></>);
    }

    return (
        <div className='Manager'>
            <Container>
                <Title text='관리자' type='1'/>
                <Row>
                    <Col lg={6} md={6} sm={6} onClick={()=>ToLink(`${match.url}/banner`)} style={{marginTop: '2rem'}}>
                        <div className={"pingpong"} style={{color: "#636363", backgroundColor:"#e7f1ff"}}>
                            배너 관리
                        </div>
                    </Col>
                    <Col lg={6} md={6} sm={6} onClick={()=>ToLink(`${match.url}/goodInfo`)} style={{marginTop: '2rem'}}>
                        <div className={"pingpong"} style={{color: "#636363", backgroundColor:"#e7f1ff"}}>
                            유용한 사이트 관리
                        </div>
                    </Col>
                    <Col lg={6} md={6} sm={6} onClick={()=>ToLink(`${match.url}/faq`)} style={{marginTop: '2rem'}}>
                        <div className={"pingpong"} style={{color: "#636363", backgroundColor:"#e7f1ff"}}>
                            자주 묻는 질문 관리
                        </div>
                    </Col>
                    <Col lg={6} md={6} sm={6} onClick={()=>ToLink(`${match.url}/userAuth`)} style={{marginTop: '2rem'}}>
                        <div className={"pingpong"} style={{color: "#636363", backgroundColor:"#e7f1ff"}}>
                            회원 관리
                        </div>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}export default Manager;
