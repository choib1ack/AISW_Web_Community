import React, {useEffect, useState, useReducer} from "react";
import Container from "react-bootstrap/Container";
import {ListButton} from "../Button/ListButton";
import Title from "../Title";
import fileImage from "../../icon/file.svg";
import likeImage from "../../icon/like.svg"
import likeGrayImage from "../../icon/like_gray.svg"
import WriteComment from "./WriteComment";
import "./Board.css"
import MakeCommentList from "./MakeCommentList";
import Loading from '../Loading';
import {useHistory} from "react-router-dom";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import axiosApi from "../../axiosApi";
import {logOut} from "../MyPage";

export default function BoardDetail({match}) {
    const [boardDetailData, setBoardDetailData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [refresh, setRefresh] = useState(0);
    const [htmlContent, setHtmlContent] = useState(null);
    const [likeState, dispatch] = useReducer(reducer, {"press": false, "num": 0});
    const [show, setShow] = useState(false);
    const {board_category, id} = match.params;
    const auth_url = (board_category === 'qna' ? 'auth-student' : 'auth');
    let history = useHistory();

    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    window.scrollTo(0, 0);

    function reducer(state, action) {
        switch (action.type) {
            case 'INITIALIZE':
                return {
                    "num": action.value_likes,
                    "press": action.value_press,
                };
            case 'PRESS':
                return {
                    num: state.num + 1,
                    press: true
                };
            case 'REMOVE':
                return {
                    num: state.num - 1,
                    press: false
                };
            default:
                return state;
        }
    }

    const Category = (c) => {
        switch (c) {
            case "free":
                return '자유게시판';
            case "qna":
                return "과목별게시판 > "+boardDetailData.subject;
            case "job":
                return "취업게시판";
        }
    }

    const handleLikeClick = async () => {
        const data = {
            "board_id": Number(id),
        }

        await axiosApi.post('/like/press', {data: data})
            .then((res) => {
                alert("게시글에 좋아요를 눌렀습니다");
                dispatch({type: 'PRESS'});
            }).catch(error => {
                let errorObject = JSON.parse(JSON.stringify(error));
                alert("좋아요 누름 에러!" + errorObject);
            })
    }

    const handleLikeCancelClick = async () => {
        await axiosApi.delete(`/like/remove/${id}?target=POST`)
            .then((res) => {
                alert("게시글에 좋아요를 취소했습니다");
                dispatch({type: 'REMOVE'});
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

    const Refresh = () => {
        setRefresh(refresh + 1);
    }

    useEffect(() => {
        const fetchNoticeData = async () => {
            try {
                setError(null);
                setLoading(true);

                const response = await axiosApi.get(`/${auth_url}/board/${board_category}/comment&like/${id}`)
                    .catch(error => {
                        let errorObject = JSON.parse(JSON.stringify(error));
                        console.log(errorObject);
                    });

                setBoardDetailData(response.data.data); // 데이터는 response.data 안에
                dispatch({
                    type: 'INITIALIZE',
                    value_likes: response.data.data.likes,
                    value_press: response.data.data.check_like
                });

                setHtmlContent(response.data.data.content);
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, [refresh]); // 여기 빈배열 안써주면 무한루프,,

    if (loading) return <Loading/>;
    if (error) return <p> 에러가 발생했습니다{error.toString()}</p>;
    if (!boardDetailData) return null;

    function handleEdit() {
        history.push({pathname: `${match.url}/edit`, state: {detail: boardDetailData, content: htmlContent}});
    }

    async function handleDelete() {
        await axiosApi.delete(`/auth-student/board/${board_category}/${id}`)
            .then((res) => {
                history.push('/board')  // BoardList로 이동
            }).catch(error => {
                let errorObject = JSON.parse(JSON.stringify(error));
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

                     style={{borderTop: 'solid 2px #0472FD', borderBottom: 'solid 2px #0472FD'}}>
                    <div style={{backgroundColor: "#e7f1ff"}} className="p-4">
                        <div>
                            <p style={{color: "#0472FD", fontSize: '12px'}}
                               className="d-inline-block mb-1 mr-2">{Category(board_category)}</p>
                            {/*<p style={{color: "#0472FD", fontSize: '12px'}}*/}
                            {/*   className="d-inline-block mb-1 mr-2"> > </p>*/}
                            {/*<p style={{color: "#0472FD", fontSize: '12px'}}*/}
                            {/*   className="d-inline-block mb-1">{boardDetailData.subject}</p>*/}
                        </div>

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

                        {likeState.press ? <span style={{float: "right", fontSize: '13px', color: '#FF6262'}}>
                                <img src={likeImage} onClick={handleLikeCancelClick}
                                     style={{cursor: "pointer"}}/> {likeState.num}</span> :
                            <span style={{float: "right", fontSize: '13px', color: '#949494'}}>
                                <img src={likeGrayImage} onClick={handleLikeClick}
                                     style={{cursor: "pointer"}}/> {likeState.num}</span>}
                        {/*{LikeComponent(boardDetailData.check_like, boardDetailData.likes)}*/}


                        <div style={{minHeight: "100px"}}
                             dangerouslySetInnerHTML={{__html: htmlContent}}/>
                    </div>
                    {AttachmentFile(boardDetailData.attachment_file)}
                    <hr/>

                    <div className="p-3">
                        <Title text='댓글' type='2'/>
                        <MakeCommentList
                            id={id}
                            Refresh={Refresh}
                            board_category={board_category}
                            board_comment_data={boardDetailData.comment_api_response_list}
                        />
                        <WriteComment
                            board_id={id}
                            board_category={board_category}
                            Refresh={Refresh}
                        />
                    </div>
                </div>
                <ListButton/>
            </Container>
        </div>
    )
}

