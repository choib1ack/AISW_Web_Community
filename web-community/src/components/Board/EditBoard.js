import {useForm} from "react-hook-form";
import React, {useState} from "react";
import {useSelector} from "react-redux";
import Container from "react-bootstrap/Container";
import FinishModal from "../FinishModal";
import Title from "../Title";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {subject_list} from "./SubjectList";
import Button from "react-bootstrap/Button";
import {checkContent, checkTitle} from "./NewBoard";
import WriteEditorContainer from "../WriteEditorContainer";
import {useHistory, useLocation} from "react-router-dom";
import axiosApi from "../../axiosApi";
import {AUTH_BOARD_PUT} from "../../constants";

function EditBoard({match}) {
    const {register, handleSubmit} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);
    const location = useLocation();
    const history = useHistory();

    const {detail, content} = location.state;
    const {board_category, id} = match.params;
    const write = useSelector(state => state.write)

    function putBoard(data, path) {
        axiosApi.put(`/${AUTH_BOARD_PUT[path]}/board/` + path,
            {data: data},
        ).then((res) => {
            setModalShow(true);
        }).catch(error => {
            console.log(error);
            alert("글 게시에 실패하였습니다.");
        })
    }

    const onSubmit = (data) => {
        data.content = write.value;
        data.board_type = board_category;

        if (checkTitle(data.title) && checkContent(data.content)) {
            let temp = {
                content: data.content,
                id: id,
                is_anonymous: detail.writer === '익명',
                status: "GENERAL",
                title: data.title
            };

            if (data.board_type === 'qna') {
                temp.subject = data.subject;
            }

            putBoard(temp, data.board_type);
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
                                <Form.Control as="select" defaultValue={board_category}
                                              id='board_category'
                                              disabled={true}
                                              name="board_type" ref={register}>
                                    <option value="free">자유게시판</option>
                                    <option value="qna">과목별게시판</option>
                                </Form.Control>
                            </Form.Group>
                        </Col>
                        <Col>
                            {board_category === "qna" &&
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

                    <div style={{justifyContent: 'space-between'}}>
                        <input ref={register} type="file" name="file" style={{float: 'left'}}/>

                        <div style={{float: "right"}}>
                            <Button variant="secondary" className="mr-2"
                                    onClick={() => history.goBack()}>
                                취소하기
                            </Button>
                            <Button variant="primary" type="submit">
                                수정하기
                            </Button>
                        </div>
                    </div>
                </Form>

            </Container>
        </div>
    );
}

export default EditBoard;
