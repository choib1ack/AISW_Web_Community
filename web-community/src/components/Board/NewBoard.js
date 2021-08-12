import React, {useRef, useState} from "react";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import classNames from 'classnames';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Title from "../Title";
import {useForm} from "react-hook-form";
import FinishModal from "../FinishModal";
import {useSelector} from "react-redux";
import {subject_list} from "./SubjectList";
import WriteEditorContainer from "../WriteEditorContainer";
import FileUpload from "../FileUpload";
import axiosApi from "../../axiosApi";

function NewBoard() {
    const {register, handleSubmit, watch} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);
    const board_type = useRef();
    board_type.current = watch("board_type");

    // redux toolkit
    const write = useSelector(state => state.write)

    async function postBoard(auth, data, path) {
        await axiosApi.post(`/${auth}/board/` + path,
            {data: data},
        ).then((res) => {
            setModalShow(true)   // 완료 모달 띄우기
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log(errorObject);
        })
    }

    const onSubmit = (data) => {
        data.content = write.value;

        if (checkTitle(data.title) && checkContent(data.content)) {
            let temp, auth;

            if (data.board_type === 'free') {
                temp = {
                    content: data.content,
                    is_anonymous: true,
                    status: "GENERAL",
                    title: data.title,
                }
                auth = 'auth';
            } else if (data.board_type === 'qna') {
                temp = {
                    content: data.content,
                    is_anonymous: true,
                    status: "GENERAL",
                    subject: data.subject,
                    title: data.title,
                }
                auth = 'auth-student';
            }

            postBoard(auth, temp, data.board_type);
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

                    <FileUpload/>

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

