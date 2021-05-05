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
import likeGrayImage from "../../icon/like_gray.svg"
import WriteComment from "./WriteComment";
import "./Board.css"
import MakeCommentList from "./MakeCommentList";
import Loading from '../Loading';
import WriteReComment from "./WriteReComment";
import {Link, useHistory} from "react-router-dom";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import {useSelector} from "react-redux";

export default function BoardDetail({match}) {
    const [boardDetailData, setBoardDetailData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [likes, setLikes] = useState(0);
    const [isLatest, setIsLatest] = useState(false);
    const [htmlContent, setHtmlContent] = useState(null);
    let history = useHistory();

    // redux toolkit
    const user = useSelector(state => state.user)

    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    window.scrollTo(0, 0);

    const {board_category} = match.params;
    const {id} = match.params;
    const url = match.url.substring(0, 11) + "/comment&like/" + id + "/" + 1;
    console.log("url!! -->", url);

    const Category = (c) => {
        switch (c) {
            case "free":
                return '자유게시판';
            case "qna":
                return "과목별게시판";
        }
    }

    const handleLikeClick = async () => {
        // account_id는 나중에 바꿔야함
        const data = {
            "account_id": 1,
            "board_id": id
            // "comment_id": 1
        }
        await axios.post('/like/press/',
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data
            }
        ).then((res) => {
            alert("게시글에 좋아요를 눌렀습니다");
            setIsLatest(false);
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            alert("좋아요 누름 에러!" + errorObject);
        })
    }
    const handleLikeCancleClick = async () => {
        // account_id는 나중에 바꿔야함
        const data = {
            "account_id": 1,
            "board_id": id
            // "comment_id": 1
        }
        await axios.post('/like/remove/',
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data
            }
        ).then((res) => {
            alert("게시글에 좋아요를 취소했습니다");
            setIsLatest(false);
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            alert("좋아요 취소 에러!" + errorObject);
        })
    }

    // 첨부파일이 있을 때만 보여줌
    const AttachmentFile = (att) => {
        if (att == null) return null;
        return (
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
                setBoardDetailData(null);
                setLoading(true);

                const response = await axios.get(url);
                setLikes(response.data.data.likes);
                setBoardDetailData(response.data.data); // 데이터는 response.data 안에
                setIsLatest(true);

                setHtmlContent(response.data.data.content);
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, [isLatest]); // 여기 빈배열 안써주면 무한루프,,

    if (loading) return <Loading/>;
    if (error) return <tr>
        <td colSpan={5}>에러가 발생했습니다{error.toString()}</td>
    </tr>;
    if (!boardDetailData) return null;

    function handleEdit() {
        history.push({pathname: `${match.url}/edit`, state: {detail: boardDetailData, content: htmlContent}});
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
                        {boardDetailData.check_like ?
                            <span style={{float: "right", fontSize: '13px', color: '#FF6262'}}>
                                <img src={likeImage} onClick={handleLikeCancleClick}
                                     style={{cursor: "pointer"}}/> {likes}</span> :
                            <span style={{float: "right", fontSize: '13px', color: '#949494'}}>
                                <img src={likeGrayImage} onClick={handleLikeClick}
                                     style={{cursor: "pointer"}}/> {likes}</span>}

                        <div className="p-3" style={{minHeight: "100px"}}
                             dangerouslySetInnerHTML={{__html: htmlContent}}/>
                    </div>
                    {AttachmentFile(boardDetailData.attachment_file)}
                    <hr/>

                    <div className="p-3">
                        <Title text='댓글' type='2'/>
                        <MakeCommentList
                            id={id}
                            isLatest={isLatest}
                            setIsLatest={setIsLatest}
                            board_category={board_category}
                            board_comment_data={boardDetailData.comment_api_response_list}
                        />
                        <WriteComment
                            board_id={id}
                            setIsLatest={setIsLatest}
                        />
                    </div>
                </div>
                <ListButton/>
            </Container>
        </div>
    )
}

