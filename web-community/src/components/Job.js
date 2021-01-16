import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import searchImage from "../icon/search.svg";
import React from "react";
import Form from "react-bootstrap/Form";
import './Job.css';

export default function Job() {
    return (
        <Container className="Notice">
            <Row style={{marginBottom: '1rem', marginTop: '2rem'}}>
                <Col className="col-align">
                    <p className={"title"} style={{marginTop: 0, marginBottom: 0}}>채용 정보</p>
                </Col>
                <Col>
                    <img src={searchImage} style={{float: "right", marginLeft: "10px", height: "25px"}}/>
                    <input type="text" className={"search-box"} placeholder={'검색'}/>
                    {/*style={{background:`url(${searchImage})`, backgroundRepeat:'no-repeat'}}>*/}
                </Col>
            </Row>
            <Row style={{marginBottom: '1rem', marginTop: '1rem'}}>
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
        </Container>
    )
}
