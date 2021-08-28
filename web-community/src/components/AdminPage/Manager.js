import React from "react";
import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {useHistory} from "react-router-dom";

function Manager({match}) {

    let history = useHistory();
    const ToLink = (url) => {
        history.push(url);
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
