import React, {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import fileImage from "../../icon/file.svg";
import Title from "../Title";
import {ListButton} from "../Button/ListButton";
import axios from "axios";
import Loading from "../Loading";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import {useHistory} from "react-router-dom";
import {useSelector} from "react-redux";

export default function NoticeDetail({match}) {
    const [noticeDetailData, setNoticeDetailData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [htmlContent, setHtmlContent] = useState(null);
    let history = useHistory();

    const user = useSelector(state => state.user.userData)
    const { notice_category, id} = match.params;
    const url = match.url;

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    window.scrollTo(0, 0);

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

    // 첨부파일이 있을 때만 보여줌
    const AttachmentFile = (att) => {
        if(att == null) return null;
        return(
            <div className="p-3">
                <p style={{color: "#0472FD", fontSize: '14px'}} className="mb-1">첨부파일</p>
                <img src={fileImage} style={{marginLeft: '5px'}} className="d-inline-block mr-1"/>
                <p style={{fontSize: '14px'}} className="d-inline-block">{att}</p>
            </div>
        );
    }

    useEffect(() => {
        const fetchNoticeData = async () => {
            try {
                setError(null);
                setNoticeDetailData(null);
                setLoading(true);
                const response = await axios.get(url);
                console.log(response.data.data.content);
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
    if (error) return <tr><td colSpan={5}>에러가 발생했습니다{error.toString()}</td></tr>;
    if (!noticeDetailData) return null;

    function handleEdit() {
        history.push({pathname: `${url}/edit`, state: {detail: noticeDetailData, content: htmlContent}});
    }

    async function handleDelete() {
        await axios.delete("/notice/" + notice_category + "/" + id)
            .then((res) => {
                console.log(res)
                history.push('/notice')  // BoardList로 이동
            }).catch(error => {
                let errorObject = JSON.parse(JSON.stringify(error));
                console.log("에러 발생");
                console.log(errorObject);
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
                        <Button variant="primary" onClick={handleDelete}>
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
                <div style={{display: "flex", fontSize: '14px', color: '#8C8C8C'}}>
                    <p style={{cursor: 'pointer', marginLeft: "auto"}}
                       onClick={handleEdit}>수정</p>
                    <p style={{cursor: 'pointer', marginLeft: "10px"}}
                       onClick={handleShow}>삭제</p>
                </div>

                <div className="text-left mb-4"
                     style={{borderTop: 'solid 2px #0472FD', borderBottom: 'solid 2px #0472FD'}}>

                    <div style={{
                        backgroundColor: "#e7f1ff",
                        paddingTop: '20px',
                        paddingLeft: '20px',
                        paddingBottom: '10px'
                    }}>
                        <p style={{color: "#0472FD", fontSize: '12px'}} className="mb-1">{Category(notice_category)}></p>
                        <p style={{fontSize: '16x'}} className="d-inline-block mr-1">{noticeDetailData.title}</p>
                        {noticeDetailData.attachment_file == null? "" : <img src={fileImage} className="d-inline-block"/>}

                        <div>
                            <p className="d-inline-block mr-3 mb-0" style={{color: "#8C8C8C", fontSize: '11px'}}>{noticeDetailData.created_by}</p>
                            <p className="d-inline-block mb-0" style={{color: "#8C8C8C", fontSize: '11px'}}>
                                {noticeDetailData.created_at.substring(0, 10)} {noticeDetailData.created_at.substring(11, 19)}
                                <span className="ml-3">조회 {noticeDetailData.views}</span>
                            </p>
                        </div>
                    </div>

                    <div className="p-3" style={{minHeight:"100px"}} dangerouslySetInnerHTML={{ __html: htmlContent }} />
                    {AttachmentFile(noticeDetailData.attachment_file)}
                </div>

                <ListButton/>
            </Container>
        </div>
    );
}
