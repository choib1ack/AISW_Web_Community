import Container from "react-bootstrap/Container";
import Title from "./Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import React from "react";
import programmersImage from "../siteImages/programmers.png";
import baekjoonImage from "../siteImages/baekjoon.png";
import lifeCodingImage from "../siteImages/life_coding.png";
import addWebPageImage from "../image/add_webpage_btn.svg";
import Button from "react-bootstrap/Button";
import BorderButton from "./Button/BorderButton";


function ManageGoodInfo({match}) {
    let style = {
        border:'1px solid #C0C0C0',
        width:'100%'
    }
    let add_btn_style = {
        border:'1px solid #E8E8E8',
        width:'100%'
    }
    return (
        <div className='Manager'>
            <Container>
                <Title text='관리자' type='1'/>
                <Title text='유용한 사이트' type='2'/>
                <Title text='코딩테스트 준비' type='3'/>
                <Row>
                    <Col lg={2} md={2} sm={2}>
                        <img src={programmersImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={baekjoonImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={lifeCodingImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={addWebPageImage} style={add_btn_style}/>
                    </Col>
                </Row>
                <Title text='온라인 강의' type='3'/>
                <Row>
                    <Col lg={2} md={2} sm={2}>
                        <img src={baekjoonImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={programmersImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={addWebPageImage} style={add_btn_style}/>
                    </Col>
                </Row>
                <Row style={{marginTop:'3rem'}}>
                    <Col>
                        <BorderButton content='+ 새 카테고리 추가하기'/>
                    </Col>
                </Row>
            </Container>
        </div>
    );
}export default ManageGoodInfo;