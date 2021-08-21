import Container from "react-bootstrap/Container";
import Title from "../Title";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {useSelector} from "react-redux";
import FinishModal from "../FinishModal";
import {checkContent, checkTitle} from "../Board/NewBoard";
import {useLocation} from "react-router-dom";
import WriteEditorContainer from "../WriteEditorContainer";
import axiosApi from "../../axiosApi";
import {AUTH_NOTICE_PUT} from "../../constants";

export default function EditNotice({match}, props) {
    const {register, handleSubmit} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);
    const location = useLocation();

    const detail = location.state.detail;
    const content = location.state.content;
    const {notice_category, id} = match.params;

    const write = useSelector(state => state.write)

    async function sendNotice(data, path) {
        await axiosApi.put(`/${AUTH_NOTICE_PUT[path]}/notice/${path}`,
            {data: data},
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
            if (notice_category === "university") {
                test = {
                    campus: "COMMON",
                    content: data.content,
                    status: "GENERAL",
                    title: data.title,
                }
            } else if (notice_category === "department") {
                test = {
                    content: data.content,
                    status: "GENERAL",
                    title: data.title,
                }
            } else if (notice_category === "council") {
                test = {
                    content: data.content,
                    status: "GENERAL",
                    title: data.title,
                }
            }
            test.id = id
            sendNotice(test, notice_category)
        }
    }

    return (
        <div className="EditNotice">
            <Container>
                <FinishModal show={modalShow} link={`/notice`}
                             title="공지사항" body="글 수정이 완료되었습니다 !"/>

                <Title text='공지사항 수정' type='1'/>
                <Form onSubmit={handleSubmit(onSubmit)} style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    <Row>
                        <Col>
                            <Form.Group>
                                <Form.Control as="select" defaultValue={notice_category} id='board_category'
                                              disabled={true}
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
                                <Form.Control type="text" defaultValue={detail.title}
                                              name="title" ref={register}
                                />
                            </Form.Group>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <WriteEditorContainer type="edit" text={content}/>
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
