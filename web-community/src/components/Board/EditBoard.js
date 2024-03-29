import {useForm} from "react-hook-form";
import React, {useCallback, useEffect, useState} from "react";
import {useSelector} from "react-redux";
import Container from "react-bootstrap/Container";
import FinishModal from "../Modal/FinishModal";
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
import {AUTH_BOARD_POST, AUTH_BOARD_PUT, BOARD_FILE_API} from "../../constants";
import {Checkbox} from "semantic-ui-react";

function EditBoard({match}) {
    const {register, handleSubmit} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);
    const location = useLocation();
    const history = useHistory();
    const [isReview, setIsReview] = useState(true);

    const {detail, content} = location.state;
    const [anonymousState, setAnonymousState] = useState(detail.writer === '익명');

    const {board_category, id} = match.params;
    const write = useSelector(state => state.write);
    const {role} = useSelector(state => state.user.decoded);

    const [files, setFiles] = useState(detail.file_api_response_list);
    const [deleteList, setDeleteList] = useState([]);

    function putBoard(data, path) {
        axiosApi.put(`/${AUTH_BOARD_PUT[path]}/board/${path}`, data)
            .then((res) => {
                setModalShow(true);
            })
            .catch(error => {
                alert("글 게시에 실패하였습니다.");
            })
    }

    const onSubmit = (data) => {
        data.content = write.value;
        data.board_type = board_category;

        if (checkTitle(data.title) && checkContent(data.content)) {
            const apiRequest = BOARD_FILE_API[data.board_type]; // 카테고리별 다르게 적용

            let formData = new FormData();
            for (let i = 0; i < data.file.length; i++) {    // 추가할 파일
                formData.append('files', data.file[i]);
            }
            for (let i = 0; i < deleteList.length; i++) {   // 지울 파일
                formData.append('delFileIds', deleteList[i]);
            }

            formData.append(`${apiRequest}.content`, data.content);
            formData.append(`${apiRequest}.id`, id);
            formData.append(`${apiRequest}.isAnonymous`, anonymousState);
            formData.append(`${apiRequest}.status`, data.board_type === 'job' ? (isReview ? 'REVIEW' : 'GENERAL') : data.status);
            formData.append(`${apiRequest}.title`, data.title);

            if (data.board_type === 'qna') {
                formData.append(`${apiRequest}.subject`, data.subject);
            }
            putBoard(formData, data.board_type, 'file');
        }
    }

    const onRemove = useCallback(id => {
            setFiles(files.filter((file) => file.id !== id));
            setDeleteList(prevState => [...prevState, id]);
        },
        [files],
    );

    const ReplaceLink = () => {
        history.goBack();
    }

    return (
        <div className="EditBoard">
            <Container>
                <FinishModal show={modalShow}
                             title="게시판" body="글 수정이 완료되었습니다 !" replace_link={ReplaceLink}/>

                <Title text='게시글 수정' type='1'/>
                <Form onSubmit={handleSubmit(onSubmit)} style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    {role === 'ROLE_ADMIN' && (board_category === 'free' || board_category === 'qna') &&
                    <Row className="pl-3 pb-3">
                        <Form.Check
                            required type="radio"
                            label="긴급"
                            name="status"
                            value="URGENT"
                            ref={register}
                            defaultChecked={detail.status === 'URGENT'}
                            className="m-1"
                        />
                        <Form.Check
                            required type="radio"
                            label="공지"
                            name="status"
                            value="NOTICE"
                            ref={register}
                            defaultChecked={detail.status === 'NOTICE'}
                            className="m-1"
                        />
                        <Form.Check
                            required type="radio"
                            label="일반"
                            name="status"
                            value="GENERAL"
                            ref={register}
                            defaultChecked={detail.status === 'GENERAL'}
                            className="m-1"
                        />
                    </Row>
                    }
                    <Row>
                        <Checkbox label='익명' checked={anonymousState}
                                  onChange={() => setAnonymousState(!anonymousState)}
                                  className="ml-4 mb-2"
                        />
                        {board_category === "job" ?
                            <Checkbox label='취업 후기' checked={isReview}
                                      onChange={() => setIsReview(!isReview)}
                                      className="ml-4 mb-2"
                            />
                            : null
                        }
                    </Row>
                    <Row>
                        <Col>
                            <Form.Group>
                                <Form.Control as="select" defaultValue={board_category}
                                              id='board_category'
                                              disabled={true}
                                              name="board_type" ref={register}>
                                    <option value="free">자유게시판</option>
                                    <option value="qna">과목별게시판</option>
                                    <option value="job">취업게시판</option>
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
                        <Col>
                            <FileList file={files} onRemove={onRemove}/>
                            <input multiple ref={register} type="file" name="file" style={{float: 'left'}}/>
                        </Col>
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

export const FileList = ({file, onRemove}) => {
    if (file.length === 0) return null;
    return (
        <Row className="ml-0">
            {
                file.map((data) => (
                    <div key={data.id} className="float-left mb-3 mr-3">
                        <button type="button" className="btn btn-sm btn-outline-danger"
                                onClick={() => onRemove(data.id)}>
                            {data.file_name}{data.name} X
                        </button>
                    </div>
                ))
            }
        </Row>
    )
}
