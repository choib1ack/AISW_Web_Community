import Container from "react-bootstrap/Container";
import Title from "../Title";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {useSelector} from "react-redux";
import FinishModal from "../FinishModal";
import {checkContent, checkTitle} from "../Board/NewBoard";
import WriteEditorContainer from "../WriteEditorContainer";
import FileUpload from "../FileUpload";
import axiosApi from "../../axiosApi";
import {AUTH_NOTICE_POST} from "../../constants";

export default function NewNotice() {
    const {register, handleSubmit} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);

    const write = useSelector(state => state.write);
    const {role} = useSelector(state => state.user.decoded);

    async function sendNotice(data, path) {
        await axiosApi.post(`/${AUTH_NOTICE_POST[path]}/notice/${path}`,
            {data: data}
        ).then((res) => {
            setModalShow(true)   // 완료 모달 띄우기
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log(errorObject);

            alert("글 게시에 실패하였습니다.") // 실패 메시지
        })
    }

    const onSubmit = (data) => {
        data.content = write.value;

        if (checkTitle(data.title) && checkContent(data.content)) {
            let test;
            if (data.board_type === "university") {
                if (role === 'ROLE_COUNCIL') {
                    alert('학생회 카테고리 외에는 글을 게시할 수 없습니다!');
                    return;
                }
                test = {
                    'campus': "COMMON",
                    'content': data.content,
                    'status': "GENERAL",
                    'title': data.title
                }
            } else if (data.board_type === "department") {
                if (role === 'ROLE_COUNCIL') {
                    alert('학생회 카테고리 외에는 글을 게시할 수 없습니다!');
                    return;
                }
                test = {
                    'content': data.content,
                    'status': "GENERAL",
                    'title': data.title
                }
            } else if (data.board_type === "council") {
                test = {
                    'content': data.content,
                    'status': "GENERAL",
                    'title': data.title
                }
            }
            sendNotice(test, data.board_type);
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
