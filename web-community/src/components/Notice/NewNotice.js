import Container from "react-bootstrap/Container";
import Title from "../Title";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import React, {useState} from "react";
import {useForm} from "react-hook-form";
import {useSelector} from "react-redux";
import FinishModal from "../Modal/FinishModal";
import {checkContent, checkTitle} from "../Board/NewBoard";
import WriteEditorContainer from "../WriteEditorContainer";
import axiosApi from "../../axiosApi";
import {AUTH_NOTICE_POST, NOTICE_FILE_API} from "../../constants";
import {useHistory} from "react-router-dom";

export default function NewNotice() {
    const {register, handleSubmit} = useForm({mode: "onChange"});
    const [modalState, setModalState] = useState({show: false, id: null, category: null});
    const history = useHistory();

    const write = useSelector(state => state.write);
    const {role} = useSelector(state => state.user.decoded);

    function postNotice(data, path) {
        axiosApi.post(`/${AUTH_NOTICE_POST[path]}/notice/${path}`, data)
            .then((res) => {
                setModalState({show: true, id: res.data.data.id, category: res.data.data.category.toLowerCase()});
            })
            .catch(error => {
                console.log(error);
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
            formData.append(`${apiRequest}.content`, data.content);
            formData.append(`${apiRequest}.status`, data.status);
            formData.append(`${apiRequest}.title`, data.title);

            if (data.board_type === 'university') {
                formData.append(`${apiRequest}.campus`, 'COMMON');
            }
            postNotice(formData, data.board_type, 'file');
        }
    }

    const ReplaceLink = () => {
        history.replace(`/notice/${modalState.category}/${modalState.id}`);
    }

    return (
        <div className="NewNotice">
            <Container>
                <FinishModal show={modalState.show}
                             replace_link={ReplaceLink}
                             title="공지사항" body="글 게시가 완료되었습니다 !"/>

                <Title text='새 공지사항 작성' type='1'/>
                <Form onSubmit={handleSubmit(onSubmit)} style={{marginTop: '3rem', marginBottom: '1rem'}}>
                    {role === 'ROLE_ADMIN' &&
                    <Row className="pl-3 pb-3">
                        <Form.Check
                            required type="radio"
                            label="긴급"
                            name="status"
                            value="URGENT"
                            ref={register}
                            className="m-1"
                        />
                        <Form.Check
                            required type="radio"
                            label="공지"
                            name="status"
                            value="NOTICE"
                            ref={register}
                            className="m-1"
                        />
                        <Form.Check
                            required type="radio"
                            label="일반"
                            name="status"
                            value="GENERAL"
                            ref={register}
                            className="m-1"
                        />
                    </Row>
                    }

                    <Row>
                        <Col>
                            <Form.Group>
                                <Form.Control as="select" defaultValue="게시판 선택" id='board_category'
                                              name="board_type" ref={register}>
                                    {role === 'ROLE_COUNCIL' ? null : <option value="university">학교 홈페이지</option>}
                                    {role === 'ROLE_COUNCIL' ? null : <option value="department">학과사무실</option>}
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

                    <Row className="pb-3">
                        <Col>
                            <div style={{justifyContent: 'space-between'}}>
                                <input multiple ref={register} type="file" name="file" style={{float: 'left'}}/>

                                <div style={{float: "right"}}>
                                    <Button variant="secondary" className="mr-2"
                                            onClick={() => history.goBack()}>
                                        취소하기
                                    </Button>
                                    <Button variant="primary" type="submit">
                                        등록하기
                                    </Button>
                                </div>
                            </div>
                        </Col>
                    </Row>
                </Form>

            </Container>
        </div>
    );
}
