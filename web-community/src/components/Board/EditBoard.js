import {useForm} from "react-hook-form";
import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import Container from "react-bootstrap/Container";
import FinishModal from "../FinishModal";
import Title from "../Title";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {subject_list} from "./SubjectList";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import {checkContent, checkTitle} from "./NewBoard";
import WriteEditorContainer from "../WriteEditorContainer";
import {useLocation} from "react-router-dom";
import axiosApi from "../../axiosApi";

function EditBoard({match}) {
    const {register, handleSubmit} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);
    const location = useLocation();

    const detail = location.state.detail;
    const content = location.state.content;
    const {board_category, id} = match.params;
    const [refreshToken, setRefreshToken] = useState(() => window.localStorage.getItem("REFRESH_TOKEN") || null);

    // redux toolkit
    const write = useSelector(state => state.write)

    async function sendBoard(auth, data, path) {
        await axiosApi.put(`/${auth}/board/` + path,
            {data: data},
        ).then((res) => {
            setModalShow(true)   // 완료 모달 띄우기
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log(errorObject);

            if (error.response.data.error === "JwtTokenExpired") {
                axiosApi.put(`/${auth}/board/` + path,
                    {data: data},
                    {
                        headers: {
                            'Refresh_Token': refreshToken
                        }
                    }
                ).then((res) => {
                    setModalShow(true)   // 완료 모달 띄우기
                }).catch(error => {
                    let errorObject = JSON.parse(JSON.stringify(error));
                    console.log(errorObject);
                })
            } else {
                alert("글 게시에 실패하였습니다.") // 실패 메시지
            }
        })
    }

    const onSubmit = (data) => {
        data.content = write.value;
        data.board_type = board_category;

        if (checkTitle(data.title) && checkContent(data.content)) {
            let temp, auth;
            if (data.board_type === 'free') {
                temp = {
                    content: data.content,
                    id: data.id,
                    is_anonymous: true,
                    status: "GENERAL",
                    title: data.title,
                }
                auth = 'auth';
            } else if (data.board_type === 'qna') {
                temp = {
                    content: data.content,
                    id: data.id,
                    is_anonymous: true,
                    status: "GENERAL",
                    subject: data.subject,
                    title: data.title,
                }
                auth = 'auth-student';
            }
            temp.id = id;

            sendBoard(auth, temp, data.board_type);
        }
    }

    return (
        <div className="EditBoard">
            <Container>
                <FinishModal show={modalShow} link={`/board`}
                             title="게시판" body="글 수정이 완료되었습니다 !"/>

                <Title text='게시글 수정' type='1'/>
                <Form onSubmit={handleSubmit(onSubmit)} style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    <Row>
                        <Col>
                            <Form.Group>
                                <Form.Control as="select" defaultValue={board_category} id='board_category'
                                              disabled={true}
                                              name="board_type" ref={register}>
                                    <option value="free">자유게시판</option>
                                    <option value="qna">과목별게시판</option>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            {board_category == "qna" &&
                            <Form.Control as="select" defaultValue={detail.subject} id='lecture'
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
                                <Form.Control type="text" defaultValue={detail.title}
                                              name="title" ref={register}/>
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

export default EditBoard;
