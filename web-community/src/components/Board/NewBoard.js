import React, {useEffect, useRef, useState} from "react";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import classNames from 'classnames';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Title from "../Title";
import {Controller, useForm} from "react-hook-form";
import axios from "axios";
import FinishModal from "../FinishModal";
import {useDispatch, useSelector} from "react-redux";
import {subject_list} from "./SubjectList";
import WriteEditorContainer from "../WriteEditorContainer";

function NewBoard() {
    const {register, handleSubmit, control, watch} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);
    const board_type = useRef();
    board_type.current = watch("board_type");

    // redux toolkit
    const user = useSelector(state => state.user.userData)
    const write = useSelector(state => state.write)
    const dispatch = useDispatch()

    async function postBoard(data, path) {
        await axios.post("/board/" + path,
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data,
            },
        ).then((res) => {
            console.log(res)
            setModalShow(true)   // 완료 모달 띄우기
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log("에러 발생");
            console.log(errorObject);

            alert("글 게시에 실패하였습니다.") // 실패 메시지
        })
    }

    const onSubmit = (data) => {
        data.content = write.value;

        if (checkTitle(data.title) && checkContent(data.content)) {
            let test;
            if (data.board_type === 'free') {
                test = {
                    attachment_file: "string",
                    content: data.content,
                    is_anonymous: true,
                    level: 0,
                    status: "GENERAL",
                    title: data.title,
                    account_id: user.id
                }
            } else if (data.board_type === 'qna') {
                test = {
                    attachment_file: "string",
                    content: data.content,
                    is_anonymous: true,
                    level: 0,
                    status: "GENERAL",
                    subject: data.subject,
                    title: data.title,
                    account_id: user.id
                }
            }
            postBoard(test, data.board_type)
        }
    }

    return (
        <div className="NewBoard">
            <Container>
                <FinishModal show={modalShow} link={`/board`}
                             title="게시판" body="글 게시가 완료되었습니다 !"/>

                <Title text='새 게시글 작성' type='1'/>
                <Form onSubmit={handleSubmit(onSubmit)} style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    <Row>
                        <Col>
                            <Form.Group>
                                <Form.Control as="select" defaultValue="게시판 선택" id='board_category'
                                              name="board_type" ref={register}>
                                    <option value="free">자유게시판</option>
                                    <option value="qna">과목별게시판</option>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            {board_type.current == "qna" &&
                            <Form.Control as="select" defaultValue="과목 선택" id='lecture'
                                          name="subject" ref={register}>
                                {subject_list.map((subject, index) => {
                                    return <option value={subject} key={index}>{subject}</option>
                                })}
                            </Form.Control>
                            }
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
                            <WriteEditorContainer type="new"/>
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

export function checkTitle(title) {
    if (title === "") {
        alert("제목을 입력하세요.");
        return false;
    }
    return true;
}

export function checkContent(content) {
    if (content === null) {
        alert("내용을 입력하세요.");
        return false;
    }
    return true;
}

function convertReadOnly(option) {
    if (option) {
        document.getElementById('lecture').readOnly = false;
    } else {
        document.getElementById('lecture').readOnly = true;
    }
}

export default NewBoard;

