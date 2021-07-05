import React from "react";
import Container from "react-bootstrap/Container";
import Title from "./Title";
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
                <Row style={{marginTop: '2rem'}}>
                    <Col lg={6} md={6} sm={6}>
                    {/*<Col lg={6} md={6} sm={6} onClick={()=>ToLink(`${match.url}/banner`)}>*/}
                        <div className={"pingpong"} style={{color: "#636363", backgroundColor:"#EFF7F9"}}>
                            배너 관리
                        </div>
                    </Col>
                    <Col lg={6} md={6} sm={6} onClick={()=>ToLink(`${match.url}/goodInfo`)}>
                        <div className={"pingpong"} style={{color: "#636363", backgroundColor:"#EFF7F9"}}>
                            유용한 사이트 관리
                        </div>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}export default Manager;