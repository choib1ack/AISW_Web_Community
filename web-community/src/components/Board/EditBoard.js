import {useForm} from "react-hook-form";
import React, {useCallback, useEffect, useState} from "react";
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
import {AUTH_BOARD_PUT, BOARD_FILE_API} from "../../constants";

function EditBoard({match}) {
    const {register, handleSubmit} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);
    const location = useLocation();
    const history = useHistory();

    const {detail, content} = location.state;
    const {board_category, id} = match.params;
    const write = useSelector(state => state.write);
    const {role} = useSelector(state => state.user.decoded);

    const [files, setFiles] = useState(detail.file_api_response_list);

    function putBoard(data, path, type) {
        if (type === 'file') {
            axiosApi.put(`/${AUTH_BOARD_PUT[path]}/board/${path}/upload`, data)
                .then((res) => {
                    setModalShow(true);
                })
                .catch(error => {
                    console.log(error);
                    alert("글 게시에 실패하였습니다.");
                })
        } else {
            axiosApi.put(`/${AUTH_BOARD_PUT[path]}/board/${path}`,
                {data: data},
            ).then((res) => {
                setModalShow(true)
            }).catch(error => {
                console.log(error);
                alert("글 게시에 실패하였습니다.");
            })
        }
    }

    useEffect(() => {
        console.log(files);
    }, [files]);

    const onSubmit = (data) => {
        data.content = write.value;
        data.board_type = board_category;

        // if (data.file.length === 0) {
            if (checkTitle(data.title) && checkContent(data.content)) {
                if (data.board_type !== 'free' && role === 'ROLE_GENERAL') {
                    alert('자유게시판 외에는 글을 게시할 수 없습니다!');
                    return;
                }

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
                putBoard(temp, data.board_type, null);
            }
        // }
        // else {
        //     const apiRequest = BOARD_FILE_API[data.board_type]; // 카테고리별 다르게 적용
        //
        //     let formData = new FormData();
        //     for (let i = 0; i < data.file.length; i++) {
        //         formData.append('files', data.file[i]);
        //     }
        //     formData.append(`${apiRequest}.content`, data.content);
        //     formData.append(`${apiRequest}.id`, id);
        //     formData.append(`${apiRequest}.isAnonymous`, detail.writer === '익명');
        //     formData.append(`${apiRequest}.status`, 'GENERAL');
        //     formData.append(`${apiRequest}.title`, data.title);
        //
        //     if (data.board_type === 'qna') {
        //         formData.append(`${apiRequest}.subject`, data.subject);
        //     }
        //     putBoard(formData, data.board_type, 'file');
        // }
    }

    const onRemove = useCallback(idx => {
            setFiles(files.filter((file, index) => index !== idx));
        },
        [files],
    );

    const handleFileChange = (e) => {
        let array = [];
        for (let i = 0; i < e.target.files.length; i++) {
            array.push(e.target.files[i]);
        }

        setFiles(files.concat(array));
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

                    <Row>
                        {/*<Col>*/}
                        {/*    <FileList file={files} onRemove={onRemove}/>*/}
                        {/*    <input multiple ref={register} type="file" name="file" style={{float: 'left'}}*/}
                        {/*           onChange={handleFileChange}/>*/}
                        {/*</Col>*/}
                        <Col>
                            <Button variant="primary" type="submit" className="float-right m-1">
                                수정하기
                            </Button>
                            <Button variant="secondary" className="float-right m-1"
                                    onClick={() => history.goBack()}>
                                취소하기
                            </Button>
                        </Col>
                    </Row>
                </Form>

            </Container>
        </div>
    );
}

export default EditBoard;

const FileList = ({file, onRemove}) => {
    if (file.length === 0) return null;
    return (
        <Row className="ml-0">
            {
                file.map((data, idx) => (
                    <div key={idx} className="float-left mb-3 mr-3">
                        <button type="button" className="btn btn-sm btn-outline-danger"
                                onClick={() => onRemove(idx)}>
                            {data.file_name}{data.name} X
                        </button>
                    </div>
                ))
            }
        </Row>
    )
}
