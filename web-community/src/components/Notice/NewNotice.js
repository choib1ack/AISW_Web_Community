import Container from "react-bootstrap/Container";
import Title from "../Title";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import React, {useEffect, useState} from "react";
import {useForm, Controller} from "react-hook-form";
import {useDispatch, useSelector} from "react-redux";
import axios from "axios";
import FinishModal from "../FinishModal";
import {checkContent, checkTitle} from "../Board/NewBoard";
import WriteEditorContainer from "../WriteEditorContainer";
import FileUpload from "../FileUpload";

export default function NewNotice() {
    const {register, handleSubmit, control} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);

    // redux toolkit
    const user = useSelector(state => state.user.userData)
    const write = useSelector(state => state.write)
    const dispatch = useDispatch()

    async function sendNotice(data, path) {
        await axios.post("/notice/" + path,
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
            if (data.board_type === "university") {
                test = {
                    attachment_file: "string",
                    campus: "COMMON",
                    content: data.content,
                    level: 0,
                    status: "GENERAL",
                    title: data.title,
                    account_id: user.id,
                    writer: "string"
                }
            } else if (data.board_type === "department") {
                test = {
                    attachment_file: "string",
                    content: data.content,
                    level: 0,
                    status: "GENERAL",
                    title: data.title,
                    account_id: user.id
                }
            } else if (data.board_type === "council") {
                test = {
                    attachment_file: "string",
                    content: data.content,
                    level: 0,
                    status: "GENERAL",
                    title: data.title,
                    account_id: user.id
                }
            }
            sendNotice(test, data.board_type)
        }
    }

    return (
        <div className="NewNotice">
            <Container>
                <FinishModal show={modalShow} link={`/notice`}
                             title="공지사항" body="글 게시가 완료되었습니다 !"/>

                <Title text='새 공지사항 작성' type='1'/>
                <Form onSubmit={handleSubmit(onSubmit)} style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    <Row>
                        <Col>
                            <Form.Group>
                                <Form.Control as="select" defaultValue="게시판 선택" id='board_category'
                                              name="board_type" ref={register}>
                                    <option value="university">학교 홈페이지</option>
                                    <option value="department">학과사무실</option>
                                    <option value="council">학생회</option>
                                </Form.Control>
                            </Form.Group>
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
