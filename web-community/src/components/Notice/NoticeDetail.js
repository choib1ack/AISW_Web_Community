import React, {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import fileImage from "../../icon/file.svg";
import Title from "../Title";
import {ListButton} from "../Button/ListButton";
import Loading from "../Loading";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import {useHistory} from "react-router-dom";
import axiosApi from "../../axiosApi";
import {AUTH_NOTICE_DELETE, AUTH_NOTICE_GET} from "../../constants";
import downloadFile from "../../features/downloadFile";
import {useDispatch} from "react-redux";
import {setActiveTab} from "../../features/menuSlice";

export default function NoticeDetail({match}) {
    const [noticeDetailData, setNoticeDetailData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [htmlContent, setHtmlContent] = useState(null);
    let history = useHistory();

    const {notice_category, id} = match.params;

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    console.log(noticeDetailData);

    window.scrollTo(0, 0);

    const active_change_dispatch = useDispatch();
    active_change_dispatch(setActiveTab(1));

    const Category = (c) => {
        switch (c) {
            case "university":
                return '학교 홈페이지 공지';
            case "department":
                return "학과사무실 공지";
            case "council":
                return "학생회 공지";
        }
    }

    useEffect(() => {
        const fetchNoticeData = async () => {
            try {
                setError(null);
                setNoticeDetailData(null);
                setLoading(true);

                const response = await axiosApi.get(`/${AUTH_NOTICE_GET[notice_category]}/notice/${notice_category}/${id}`);

                setNoticeDetailData(response.data.data); // 데이터는 response.data 안에
                setHtmlContent(response.data.data.content);
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, []); // 여기 빈배열 안써주면 무한루프,,

    if (loading) return <Loading/>;
    if (!noticeDetailData) return null;

    function handleEdit() {
        history.push({pathname: `${match.url}/edit`, state: {detail: noticeDetailData, content: htmlContent}});
    }

    function deleteNotice() {
        axiosApi.delete(`/${AUTH_NOTICE_DELETE[notice_category]}/notice/${notice_category}/${id}`)
            .then((res) => {
                history.push('/notice');
            })
            .catch(error => {
                console.log(error);
            })
    }

    function CustomModal() {
        return (
            <>
                <Modal show={show} onHide={handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>삭제</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>정말로 삭제 하시겠습니까 ?</Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleClose}>
                            아니오
                        </Button>
                        <Button variant="primary" onClick={deleteNotice}>
                            네
                        </Button>
                    </Modal.Footer>
                </Modal>
            </>
        )
    }

    return (
        <div className="NoticeDetail">
            <CustomModal/>

            <Container>
                <Title text='공지사항' type='1'/>

                {noticeDetailData && noticeDetailData.is_writer &&
                <div style={{display: "flex"}}>
                    <p className="edit-btn"
                       style={{marginLeft: "auto"}}
                       onClick={handleEdit}>수정</p>
                    <p className="delete-btn"
                       style={{marginLeft: "10px"}}
                       onClick={handleShow}>삭제</p>
                </div>
                }

                <div className="text-left mb-4"
                     style={{borderTop: 'solid 2px #0472FD', borderBottom: 'solid 2px #0472FD'}}>

                    <div style={{
                        backgroundColor: "#e7f1ff",
                        paddingTop: '20px',
                        paddingLeft: '20px',
                        paddingBottom: '10px'
                    }}>
                        <p style={{color: "#0472FD", fontSize: '12px'}}
                           className="mb-1">{Category(notice_category)}</p>
                        <p style={{fontSize: '16x'}} className="d-inline-block mr-1">{noticeDetailData.title}</p>
                        {noticeDetailData.file_api_response_list[0] == null ? "" :
                            <img src={fileImage} className="d-inline-block"/>}

                        <div>
                            <p className="d-inline-block mr-3 mb-0"
                               style={{color: "#8C8C8C", fontSize: '11px'}}>{noticeDetailData.created_by}</p>
                            <p className="d-inline-block mb-0" style={{color: "#8C8C8C", fontSize: '11px'}}>
                                {noticeDetailData.created_at.substring(0, 10)} {noticeDetailData.created_at.substring(11, 19)}
                                <span className="ml-3">조회 {noticeDetailData.views}</span>
                            </p>
                        </div>
                    </div>

                    <div className="p-3" style={{minHeight: "100px"}} dangerouslySetInnerHTML={{__html: htmlContent}}/>
                    {AttachmentFile(noticeDetailData.file_api_response_list)}
                </div>

                <ListButton/>
            </Container>
        </div>
    );
}

// 첨부파일이 있을 때만 보여줌
export const AttachmentFile = (att) => {
    if (att.length === 0) return null;
    return (
        <div className="p-3">
            <p style={{color: "#0472FD", fontSize: '14px'}} className="mb-1">첨부파일</p>
            {att.map(data => (
                <div>
                    <img src={fileImage} style={{marginLeft: '5px'}} className="d-inline-block mr-1"/>
                    <a className="d-inline-block filename-style"
                       onClick={() => downloadFile(data)}>
                        {data.file_name}
                    </a>
                </div>
            ))}
        </div>
    );
}
