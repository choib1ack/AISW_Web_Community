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
import WriteEditorContainer from "../WriteEditorContainer";
import axiosApi from "../../axiosApi";
import {AUTH_NOTICE_POST, NOTICE_FILE_API} from "../../constants";

export default function NewNotice() {
    const {register, handleSubmit} = useForm({mode: "onChange"});
    const [modalShow, setModalShow] = useState(false);

    const write = useSelector(state => state.write);
    const {role} = useSelector(state => state.user.decoded);

    function postNotice(data, path, type) {
        if (type === 'file') {
            axiosApi.post(`/${AUTH_NOTICE_POST[path]}/notice/${path}/upload`, data)
                .then((res) => {
                    setModalShow(true);
                })
                .catch(error => {
                    console.log(error);
                    alert("글 게시에 실패하였습니다.");
                })
        } else {
            axiosApi.post(`/${AUTH_NOTICE_POST[path]}/notice/${path}`,
                {data: data}
            ).then((res) => {
                setModalShow(true);
            }).catch(error => {
                console.log(error);
                alert("글 게시에 실패하였습니다.");
            })
        }
    }

    const onSubmit = (data) => {
        data.content = write.value;

        if (data.file.length === 0) {   // 파일이 없을 경우
            if (checkTitle(data.title) && checkContent(data.content)) {
                if (data.board_type !== 'council' && role === 'ROLE_COUNCIL') {
                    alert('학생회 카테고리 외에는 글을 게시할 수 없습니다!');
                    return;
                }

                let temp = {
                    content: data.content,
                    status: 'GENERAL',
                    title: data.title
                };

                if (data.board_type === 'university') {
                    temp.campus = 'COMMON';
                }

                postNotice(temp, data.board_type, null);
            }
        } else {
            const apiRequest = NOTICE_FILE_API[data.board_type]; // 카테고리별 다르게 적용

            let formData = new FormData();
            formData.append('files', data.file[0]);
            formData.append(`${apiRequest}.content`, data.content);
            formData.append(`${apiRequest}.isAnonymous`, true);
            formData.append(`${apiRequest}.status`, 'GENERAL');
            formData.append(`${apiRequest}.title`, data.title);

            if (data.board_type === 'university') {
                formData.append(`${apiRequest}.campus`, 'COMMON');
            }
            postNotice(formData, data.board_type, 'file');
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

                    <div style={{justifyContent: 'space-between'}}>
                        <input ref={register} type="file" name="file" style={{float: 'left'}}/>

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
                </Form>

            </Container>
        </div>
    );
}
