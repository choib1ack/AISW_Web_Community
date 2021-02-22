import React, {useState} from "react";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import classNames from 'classnames';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Title from "../Title";

function NewBoard() {
    return (
        <div className="NewBoard">
            <Container>
                <Title text='새 게시글 작성' type='1'/>
                <Form style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    <Row>
                        <Col>
                            <Form.Group controlId="category">
                                <Form.Control as="select" defaultValue="게시판 선택" id='board_category'>
                                    <option>자유게시판</option>
                                    <option>과목별게시판</option>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Control as="select" defaultValue="과목 선택" id='lecture'>
                                <option>컴퓨터 프로그래밍</option>
                                <option>안드로이드 프로그래밍</option>
                                <option>네트워크</option>
                            </Form.Control>
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
                                <Form.Control className="p-3" as="textarea" rows={3} placeholder="#태그입력"/>
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

function convertReadOnly(option){
    if(option){
        document.getElementById('lecture').readOnly = false;
    }else{
        document.getElementById('lecture').readOnly = true;
    }
}

export default NewBoard;

