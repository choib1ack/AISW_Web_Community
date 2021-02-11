import Container from "react-bootstrap/Container";
import Title from "../Title";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import React from "react";

export default function NewNotice() {
    return (
        <div className="NewNotice">
            <Container>
                <Title text='새 공지사항 작성' type='1'/>
                <Form style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    <Row>
                        <Col>
                            <Form.Group controlId="category">
                                <Form.Control as="select" defaultValue="게시판 선택" id='board_category'>
                                    <option>학교 홈페이지</option>
                                    <option>학과사무실</option>
                                    <option>학생회</option>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="subject">
                                <Form.Control type="text" placeholder="제목을 입력해주세요." />
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="content">
                                <Form.Control className="p-3" as="textarea" rows={20} placeholder="내용을 입력해주세요."/>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Button variant="primary" type="submit" style={{float:'right'}}
                                    className={classNames("select-btn", "on")}>
                                등록하기
                            </Button>
                        </Col>
                    </Row>
                </Form>


            </Container>
        </div>
    );
}
