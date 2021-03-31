import React, {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import userImage from "../../icon/user.svg";
import {ListButton} from "../Button/ListButton";
import arrowImage from "../../icon/comment_replay.png";
import Title from "../Title";
import fileImage from "../../icon/file.svg";
import axios from "axios";
import likeImage from "../../icon/like.svg"
import WriteComment from "./WriteComment";
import "./Board.css"
import MakeCommentList from "./MakeCommentList";
import Loading from '../Loading';
import {Link, useHistory} from "react-router-dom";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

export default function BoardDetail({match}) {
    const [boardDetailData, setBoardDetailData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [likes, setLikes] = useState(0);
    const [isCommentLatest, setIsCommentLatest] = useState(false);
    let history = useHistory();

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    window.scrollTo(0, 0);

    const {board_category} = match.params;
    const {id} = match.params;
    const url = match.url;

    const Category = (c) => {
        switch (c) {
            case "free":
                return '자유게시판';
            case "qna":
                return "과목별게시판";
        }
    }
    const handleLikeCilck = async () => {
        let this_url = '/board/' + board_category + '/likes/' + id;
        console.log(this_url);
        await axios.get(this_url)
            .then((res) => {
                alert("게시글에 좋아요를 눌렀습니다");
                setLikes(res.data.data.likes);

            }).catch(error => {
                alert("에러!");
                console.log(error);
            })
    }

    // 첨부파일이 있을 때만 보여줌
    const AttachmentFile = (att) => {
        if (att == null) return null;
        return (
            <div className="p-3">
                <p style={{color: "#6CBACB", fontSize: '14px'}} className="mb-1">첨부파일</p>
                <img src={fileImage} style={{marginLeft: '5px'}} className="d-inline-block mr-1"/>
                <p style={{fontSize: '14px'}} className="d-inline-block">{att}</p>
            </div>
        );
    }

    useEffect(() => {
        const fetchNoticeData = async () => {
            try {
                setError(null);
                setBoardDetailData(null);
                setLoading(true);

                const response = await axios.get(url);
                setLikes(response.data.data.likes);
                setBoardDetailData(response.data.data); // 데이터는 response.data 안에
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, []); // 여기 빈배열 안써주면 무한루프,,

    if (loading) return <Loading/>;
    if (error) return <tr>
        <td colSpan={5}>에러가 발생했습니다{error.toString()}</td>
    </tr>;
    if (!boardDetailData) return null;

    function handleEdit() {
        history.push(`${url}/edit`);
    }

    async function handleDelete() {
        await axios.delete("/board/" + board_category + "/" + id)
            .then((res) => {
                console.log(res)
                history.push('/board')  // BoardList로 이동
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
        <div className="BoardDetail">
            <CustomModal/>

            <Container>
                <Title text='게시판' type='1'/>
                <div style={{display: "flex", fontSize: '14px', color: '#8C8C8C'}}>
                    <p style={{cursor: 'pointer', marginLeft: "auto"}}
                       onClick={handleEdit}>수정</p>
                    <p style={{cursor: 'pointer', marginLeft: "10px"}}
                       onClick={handleShow}>삭제</p>
                </div>

                <div className="text-left mb-4"
                     style={{borderTop: 'solid 2px #6CBACB', borderBottom: 'solid 2px #6CBACB'}}>
                    <div style={{backgroundColor: "#EFF7F9"}} className="p-4">
                        <p style={{color: "#6CBACB", fontSize: '12px'}}
                           className="mb-1">{Category(board_category)}</p>
                        <p style={{fontSize: '16px'}} className="d-inline-block mr-1">{boardDetailData.title}</p>
                        {boardDetailData.attachment_file == null ? "" :
                            <img src={fileImage} className="d-inline-block"/>}
                        <div>
                            <p className="d-inline-block mr-3 mb-0" style={{color: "#8C8C8C", fontSize: '13px'}}>
                                {boardDetailData.is_anonymous ? "익명" : boardDetailData.created_by}
                            </p>
                            <p className="d-inline-block mb-0" style={{color: "#8C8C8C", fontSize: '13px'}}>
                                {boardDetailData.created_at.substring(0, 10)} {boardDetailData.created_at.substring(11, 19)}
                                <span className="ml-3">조회 {boardDetailData.views}</span>
                            </p>
                        </div>
                    </div>

                    <div className="p-4" style={{minHeight: "100px"}}>
                        {/*좋아요*/}
                        <span style={{float: "right", fontSize: '13px', color: '#FF6262'}}>
                                <img src={likeImage} onClick={handleLikeCilck}
                                     style={{cursor: "pointer"}}/> {likes}</span>
                        <p>{boardDetailData.content}</p>
                    </div>
                    {AttachmentFile(boardDetailData.attachment_file)}
                    <hr/>

                    <div className="p-3">
                        <Title text='댓글' type='2'/>
                        <MakeCommentList
                            id={id}
                            isLatest={isCommentLatest}
                            setIsLatest={setIsCommentLatest}
                        />
                        <ReplyBox/>
                        <ReplyBox/>
                        <WriteComment
                            board_id={id}
                            setIsLatest={setIsCommentLatest}
                        />
                    </div>
                </div>
                <ListButton/>
            </Container>
        </div>
    )
}

export function ReplyBox() {
    return (
        <div style={{display: 'flex', flexDirection: 'row'}} className="ml-5">
            <img src={arrowImage} style={{height: "20px", opacity: '0.7'}} className="ml-3 mt-3"/>
            <Card style={{borderRadius: '10px', backgroundColor: '#F9F9F9'}}
                  className="text-left flex-row m-2 border-0 w-100">
                <img src={userImage} style={{height: "30px"}} className="ml-3 align-self-start mt-3"/>

                <Card.Body>
                    <span style={{float: "right", fontSize: '13px', color: '#FF6262'}}>
                        <img src={likeImage} style={{cursor: "pointer"}}/> 0
                    </span>
                    <Card.Title className="mb-2" style={{fontSize: '14px'}}>익명1
                        <span style={{color: "#8C8C8C", fontSize: '12px', marginLeft: "10px"}}>01/09 11:10</span>
                    </Card.Title>
                    <Card.Text className="mb-0">
                        <span className={'delete-style'}>삭제</span>
                        <p className="d-inline-block mr-3 mb-1" style={{fontSize: '14px'}}>
                            네..
                        </p>
                    </Card.Text>
                </Card.Body>
            </Card>
        </div>
    )
}

