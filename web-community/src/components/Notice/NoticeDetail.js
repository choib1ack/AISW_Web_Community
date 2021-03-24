import React, {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import fileImage from "../../icon/file.svg";
import Title from "../Title";
import {ListButton} from "../Button/ListButton";
import axios from "axios";
import Loading from "../Loading";

export default function NoticeDetail({match}) {
    const [noticeDetailData, setNoticeDetailData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const { notice_category } = match.params;
    const { id } = match.params;
    const url = match.url;

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
                console.log(response.data);
                setNoticeDetailData(response.data.data); // 데이터는 response.data 안에
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

    return (
        <div className="NoticeDetail">

            <Container>
                <Title text='공지사항' type='1'/>
                <div className="text-left mt-5 mb-4"
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

                    <div className="p-3" style={{minHeight:"100px"}}>
                        <p>{noticeDetailData.content}​</p>
                    </div>
                    {AttachmentFile(noticeDetailData.attachment_file)}
                </div>

                <ListButton/>
            </Container>
        </div>
    );
}
