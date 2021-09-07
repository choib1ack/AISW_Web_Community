import Container from "react-bootstrap/Container";
import Title from "../Title";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import React, {useCallback, useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {useSelector} from "react-redux";
import FinishModal from "../Modal/FinishModal";
import {checkContent, checkTitle} from "../Board/NewBoard";
import {useHistory, useLocation} from "react-router-dom";
import WriteEditorContainer from "../WriteEditorContainer";
import axiosApi from "../../axiosApi";
import {AUTH_NOTICE_POST, AUTH_NOTICE_PUT, NOTICE_FILE_API} from "../../constants";
import {FileList} from "../Board/EditBoard";

export default function EditNotice({match}) {
    const {register, handleSubmit} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);
    const location = useLocation();
    const history = useHistory();

    const {detail, content} = location.state;
    const {notice_category, id} = match.params;

    const write = useSelector(state => state.write);
    const {role} = useSelector(state => state.user.decoded);

    const [files, setFiles] = useState(detail.file_api_response_list);
    const [deleteList, setDeleteList] = useState([]);

    function putNotice(data, path) {
        axiosApi.put(`/${AUTH_NOTICE_PUT[path]}/notice/${path}`, data)
            .then((res) => {
                setModalShow(true);
            })
            .catch(error => {
                alert("글 게시에 실패하였습니다.");
            })
    }

    const onSubmit = (data) => {
        data.content = write.value;
        if (role !== 'ROLE_ADMIN') {
            data.status = 'GENERAL';
        }

        if (checkTitle(data.title) && checkContent(data.content)) {
            const apiRequest = NOTICE_FILE_API[data.board_type]; // 카테고리별 다르게 적용

            let formData = new FormData();
            for (let i = 0; i < data.file.length; i++) {
                formData.append('files', data.file[i]);
            }
            for (let i = 0; i < deleteList.length; i++) {   // 지울 파일
                formData.append('delFileIds', deleteList[i]);
            }

            formData.append(`${apiRequest}.content`, data.content);
            formData.append(`${apiRequest}.id`, id);
            formData.append(`${apiRequest}.status`, data.status);
            formData.append(`${apiRequest}.title`, data.title);

            if (data.board_type === 'university') {
                formData.append(`${apiRequest}.campus`, 'COMMON');
            }
            putNotice(formData, data.board_type);
        }
    }

    const onRemove = useCallback(id => {
            setFiles(files.filter((file) => file.id !== id));
            setDeleteList(prevState => [...prevState, id]);
        },
        [files],
    );

    useEffect(() => {
        console.log(deleteList);
    }, [deleteList]);

    const ReplaceLink = () => {
        history.goBack();
    }

    return (
        <div className="EditNotice">
            <Container>
                <FinishModal show={modalShow} replace_link={ReplaceLink}
                             title="공지사항" body="글 수정이 완료되었습니다 !"/>

                <Title text='공지사항 수정' type='1'/>
                <Form onSubmit={handleSubmit(onSubmit)} style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    {role === 'ROLE_ADMIN' &&
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

                    <div style={{justifyContent: 'space-between'}}>
                        <Col>
                            <FileList file={files} onRemove={onRemove}/>
                            <input multiple ref={register} type="file" name="file" style={{float: 'left'}}/>
                        </Col>

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
