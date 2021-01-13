import React from 'react';
import './Menu.css';
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Grid from "@material-ui/core/Grid";

export default function Menu() {
    return (
        <Grid>
            <Row style={{borderBottom: 'solid 1px #d0d0d0'}}>
                <Col xs={3} style={{padding: '15px'}}>
                    <button className="Menu-logo">
                        가천대학교 AI&소프트웨어학부
                    </button>
                </Col>
                <Col xs={6} style={{padding: '15px'}}>
                    <button className="Menu-button">
                        공지사항
                    </button>
                    <button className="Menu-button">
                        게시판
                    </button>
                    <button className="Menu-button">
                        학과정보
                    </button>
                    <button className="Menu-button">
                        채용정보
                    </button>
                    <button className="Menu-button">
                        공모전/대외활동
                    </button>
                </Col>
                <Col xs={3} style={{padding: '15px'}}>
                    <button className="Menu-button">
                        로그인
                    </button>
                    <button className="Menu-button blue-button">
                        회원가입
                    </button>
                </Col>
            </Row>
        </Grid>
    );
}
