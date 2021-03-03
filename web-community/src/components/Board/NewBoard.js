import React, {useState} from "react";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import classNames from 'classnames';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Title from "../Title";
import {useForm} from "react-hook-form";
import axios from "axios";

function NewBoard() {
    const {register, handleSubmit, watch, errors, setValue} = useForm();

    async function sendServer(data) {
        await axios.post("/board/free",
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data,
            },
        ).then((res) => {
            console.log(res)
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log("에러 발생");
            console.log(errorObject);

            alert("글 게시에 실패하였습니다.") // 회원가입 실패 메시지
        })
    }

    const onSubmit = (data) => {
        // if (data.board_type === 'free') {
        //
        // } else if (data.board_type === 'qna') {
        //
        // }

        const test = {
            attachment_file: "string",
            content: data.content,
            // id: 0,
            is_anonymous: true,
            level: 0,
            status: "URGENT",
            title: data.title,
            user_id: 1
        }

        sendServer(test)
    }

    return (
        <div className="NewBoard">
            <Container>
                <Title text='새 게시글 작성' type='1'/>
                <Form onSubmit={handleSubmit(onSubmit)} style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    <Row>
                        <Col>
                            <Form.Group controlId="category">
                                <Form.Control as="select" defaultValue="게시판 선택" id='board_category'
                                              name="board_type" ref={register}>
                                    <option value="free">자유게시판</option>
                                    <option value="qna">과목별게시판</option>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Control as="select" defaultValue="과목 선택" id='lecture'
                                          name="subject" ref={register}>
                                <option value="computer_programming">컴퓨터 프로그래밍</option>
                                <option value="android_programming">안드로이드 프로그래밍</option>
                                <option value="network">네트워크</option>
                            </Form.Control>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="subject">
                                <Form.Control type="text" placeholder="제목을 입력해주세요."
                                              name="title" ref={register}/>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group controlId="content">
                                <Form.Control className="p-3" as="textarea" rows={20} placeholder="내용을 입력해주세요."
                                              name="content" ref={register}/>
                                <Form.Control className="p-3" as="textarea" rows={3} placeholder="#태그입력"/>
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Button variant="primary" type="submit" style={{float: 'right'}}
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

function convertReadOnly(option) {
    if (option) {
        document.getElementById('lecture').readOnly = false;
    } else {
        document.getElementById('lecture').readOnly = true;
    }
}

export default NewBoard;

