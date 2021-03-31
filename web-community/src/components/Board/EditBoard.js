import {Controller, useForm} from "react-hook-form";
import React, {useRef, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import axios from "axios";
import Container from "react-bootstrap/Container";
import FinishModal from "../FinishModal";
import Title from "../Title";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {subject_list} from "./SubjectList";
import TextEditor from "../TextEditor";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import {checkContent, checkTitle} from "./NewBoard";

function EditBoard(){
    const {register, handleSubmit, control, watch} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);
    const board_type = useRef();
    board_type.current = watch("board_type");

    // redux toolkit
    const user = useSelector(state => state.user.userData)
    const dispatch = useDispatch()

    async function sendBoard(data, path) {
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
            sendBoard(test, data.board_type)
        }
    }

    return (
        <div className="NewBoard">
            <Container>
                <FinishModal show={modalShow} link={`/board`}
                             title="게시판" body="글 게시가 완료되었습니다 !"/>

                <Title text='게시글 수정' type='1'/>
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
                            <Form.Group controlId="content">
                                <Controller
                                    as={<TextEditor/>}
                                    name="content"
                                    control={control}
                                />
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Button variant="primary" type="submit" style={{float: 'right'}}
                                    className={classNames("select-btn", "on")}>
                                수정하기
                            </Button>
                        </Col>
                    </Row>
                </Form>


            </Container>
        </div>
    );
}

export default EditBoard;
