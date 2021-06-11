import React from "react";
import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import MakeBoardList from "../Board/MakeBoardList";
import Table from "react-bootstrap/Table";

function Bannner() {
    return (
        <div>
            <Container>
                <Title text='관리자' type='1'/>
                <Row>
                    <Col>
                        <Title text='배너 관리' type='2'/>
                    </Col>
                    <Col>
                        <Button variant="secondary" style={{marginTop: '3rem', float: 'right', width: '50px'}}
                                size='sm'>
                            변경
                        </Button>
                    </Col>
                </Row>

                <Row style={{margin: "20px 0px"}}>
                    <div style={{
                        border: "1px solid #E3E3E3", width: "100%", height: "100px",
                        display: "flex", justifyContent: "center", alignItems: "center"
                    }}>
                        <p style={{color: "#636363"}}>미리보기</p>
                    </div>
                    <Col className="p-3">
                        <div style={{float: 'right', color: "#636363"}}>
                            <p className="d-inline-block mr-2">
                                최종 수정일:
                            </p>
                            <p className="d-inline-block">
                                2016-10-31 23:59:59
                            </p>
                        </div>
                    </Col>
                </Row>

                <Row>
                    <Col>
                        <p style={{
                            fontSize: '14px',
                            textAlign: 'left',
                            marginTop: '4rem',
                            fontWeight: 'bold'
                        }}>
                            배너 변경 내역
                        </p>
                    </Col>
                    <Col>
                        <Button style={{marginTop: '3rem', float: 'right', width: '50px'}} size='sm'>
                            등록
                        </Button>
                    </Col>
                </Row>

                <div id="banner" className="pt-3">
                    <Table>
                        <thead>
                        <tr>
                            <th style={{width: "20%"}}>게시 날짜</th>
                            <th style={{width: "20%"}}>배너명</th>
                            <th style={{width: "40%"}}>게시 기간</th>
                            <th style={{width: "10%"}}>게시 여부</th>
                            <th style={{width: "20%"}}></th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td>
                                2021-05-21
                            </td>
                            <td>
                                중간고사 간식행사
                            </td>
                            <td>
                                2021-05-22 ~ 2021-06-05
                            </td>
                            <td>
                                O / X
                            </td>
                            <td>
                                <Button size='sm'>
                                    수정
                                </Button>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                </div>

            </Container>
        </div>
    );
}

export default Bannner;
