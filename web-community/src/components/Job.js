import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import searchImage from "../icon/search.svg";
import React from "react";
import './Job.css';
import Card from "react-bootstrap/Card";
import placeImage from "../icon/place.svg";
import monitorImage from "../icon/monitor.svg";

export default function Job() {
    return (
        <Container className="Job">
            <Row style={{marginBottom: '2rem'}}>
                <Col className="col-align">
                    <p className={"title"} style={{marginTop: 0, marginBottom: 0}}>채용 정보</p>
                </Col>
                <Col>
                    <img src={searchImage} style={{float: "right", marginLeft: "10px", height: "25px"}}/>
                    <input type="text" className={"search-box"} placeholder={'검색'}/>
                </Col>
            </Row>
            <Row style={{marginBottom: '2rem'}}>
                <Button className={classNames("select-btn", "on")}>JAVA</Button>
                <Button className={classNames("select-btn", "off")}>Spring</Button>
                <Button className={classNames("select-btn", "off")}>Node.js</Button>
                <Button className={classNames("select-btn", "off")}>Django</Button>
                <Button className={classNames("select-btn", "off")}>React.js</Button>
                <Button className={classNames("select-btn", "off")}>Vue.js</Button>
                <Button className={classNames("select-btn", "off")}>Javascript</Button>
                <Button className={classNames("select-btn", "off")}>Python</Button>
                <Button className={classNames("select-btn", "off")}>Kotlin</Button>
                <Button className={classNames("select-btn", "off")}>C++</Button>
                <Button className={classNames("select-btn", "off")}>웹 풀스택</Button>
                <Button className={classNames("select-btn", "off")}>웹 프론트엔드</Button>
                <Button className={classNames("select-btn", "off")}>웹 백엔드</Button>
                <Button className={classNames("select-btn", "off")}>안드로이드</Button>
                <Button className={classNames("select-btn", "off")}>ios</Button>
                <Button className={classNames("select-btn", "off")}>서버/백엔드</Button>
            </Row>

            <Row className="mb-3">
                <Col className="pl-0 pr-0">
                    <JobCard/>
                </Col>
                <Col className="pl-0 pr-0">
                    <JobCard/>
                </Col>
            </Row>
        </Container>
    )
}

function JobCard() {
    return (
        <Card style={{width: '550px'}} className="text-left flex-row">
            <img src={searchImage} style={{width: "150px", height: "150px"}}
                 className="ml-3 align-self-center"/>

            <Card.Body>
                <Card.Title>커머스 서버 개발 전문가</Card.Title>
                <Card.Subtitle className="mt-2 mb-4 text-muted">중고나라</Card.Subtitle>

                <Card.Text className="mb-0">
                    <div className="d-inline-block">
                        <img src={placeImage} style={{width: "22px", height: "22px", marginRight: "5px"}}/>
                        <p className="d-inline-block mr-3" style={{fontSize: '13px'}}>
                            서울시 강남구
                        </p>
                    </div>
                    <div className="d-inline-block">
                        <img src={monitorImage} style={{width: "16px", height: "16px", marginRight: "5px"}}/>
                        <p className="d-inline-block" style={{fontSize: '13px'}}>
                            신입, 인턴
                        </p>
                    </div>
                </Card.Text>

                <Button size="sm" disabled="true" className={classNames("select-btn", "on")}>서버/백엔드</Button>
                <Button size="sm" disabled="true" className={classNames("select-btn", "on")}>Spring</Button>
            </Card.Body>
        </Card>
    )
}
