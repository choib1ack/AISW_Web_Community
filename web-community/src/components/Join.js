import Form from "react-bootstrap/Form";
import React from "react";
import './Join.css';
import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import {SelectButton} from "./Board/BoardList";
import classNames from "classnames";

export default function Join({match}) {
    return (
        <Container className="p-5">
            <h3 className="font-weight-bold mb-5">
                회원가입
            </h3>

            <Row>
                <Col/>
                <Col sm={12} md={10} lg={8}>
                    <Form className="text-left">
                        <Form.Group controlId="formGridEmail">
                            <Form.Label>이메일</Form.Label>
                            <Form.Control type="email" placeholder="Enter email"/>
                        </Form.Group>
                        <Form.Group controlId="formGridPassword">
                            <Form.Label>비밀번호</Form.Label>
                            <Form.Control type="password" placeholder="Password"/>
                        </Form.Group>

                        <Form.Row>
                            <Form.Group sm={9} as={Col} controlId="formGridAddress1">
                                <Form.Label>이름</Form.Label>
                                <Form.Control placeholder="ex) 홍길동"/>
                            </Form.Group>

                            <Form.Group as={Col} style={{alignSelf: 'center'}}>
                                <Form.Label/>
                                <Form.Row>
                                    <Col style={{textAlign: 'center'}}>
                                        <Form.Check
                                            type="radio"
                                            label="남"
                                            name="formHorizontalRadios"
                                            id="formHorizontalRadios1"
                                        />
                                    </Col>
                                    <Col>
                                        <Form.Check
                                            type="radio"
                                            label="여"
                                            name="formHorizontalRadios"
                                            id="formHorizontalRadios2"
                                        />
                                    </Col>
                                </Form.Row>
                            </Form.Group>
                        </Form.Row>

                        <Form.Group controlId="formGridAddress2">
                            <Form.Label>전화번호</Form.Label>
                            <Form.Control placeholder="ex) 010-0000-0000"/>
                        </Form.Group>

                        <Form.Row>
                            <Form.Group as={Col} controlId="formGridState">
                                <Form.Label>직업</Form.Label>
                                <Form.Control as="select">
                                    <option>재학생</option>
                                    <option>졸업생</option>
                                    <option>학생회</option>
                                    <option>직원</option>
                                </Form.Control>
                            </Form.Group>

                            <Form.Group as={Col} controlId="formGridZip">
                                <Form.Label>학번</Form.Label>
                                <Form.Control placeholder="ex) 201533662"/>
                            </Form.Group>

                            <Form.Group as={Col}>
                                <Form.Label>학년</Form.Label>
                                <Form.Control as="select" default="해당없음">
                                    <option>해당없음</option>
                                    <option>1학년</option>
                                    <option>2학년</option>
                                    <option>3학년</option>
                                    <option>4학년</option>
                                </Form.Control>
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col} controlId="formGridZip">
                                <Form.Label>단과대학</Form.Label>
                                <Form.Control placeholder="ex) IT융합대학"/>
                            </Form.Group>
                            <Form.Group as={Col} controlId="formGridZip">
                                <Form.Label>학과</Form.Label>
                                <Form.Control placeholder="ex) 소프트웨어학과"/>
                            </Form.Group>
                        </Form.Row>

                        <Form.Group id="formGridCheckbox" style={{textAlign: 'right', marginTop: '50px'}}>
                            <Form.Check type="checkbox" label="개인정보 수집에 동의합니다."/>
                        </Form.Group>

                        <div style={{display: 'flex', alignItems: 'flex-end', justifyContent: 'flex-end'}}>
                            <Button className={classNames("select-btn", "off")} style={{width: '80px'}}>취소</Button>
                            <Button className={classNames("select-btn", "on")} style={{width: '80px'}}>확인</Button>
                        </div>
                    </Form>
                </Col>
                <Col/>
            </Row>

        </Container>
    );
}
