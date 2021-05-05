import React, {useEffect, useState} from "react";
import axios from "axios";
import Card from "react-bootstrap/Card";
import userImage from "../../icon/user.svg";
import likeImage from "../../icon/like.svg";
import likeGrayImage from "../../icon/like_gray.svg"
import Loading from "../Loading";
import WriteReComment from "./WriteReComment";
import MakeReCommentList from "./MakeReCommentList";

export default function MakeCommentList({id, Refresh, board_comment_data}) {
    const [boardCommentData, setBoardCommentData] = useState(board_comment_data);
    // const [loading, setLoading] = useState(false);
    // const [error, setError] = useState(null);


    const dateFormat = (s) => {
        let result = "";
        result += s.substring(5, 7) + "/" + s.substring(8, 10) + "  " + s.substring(11, 16);
        return result;
    }

    const handleLikeClick = async (comment_id) => {
        // account_id는 나중에 바꿔야함
        console.log(comment_id);
        const data = {
            "account_id": 1,
            // "board_id": id
            "comment_id": comment_id
        }
        await axios.post('/like/press/',
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data
            }
        ).then((res) => {
            alert("댓글에 좋아요를 눌렀습니다");
            // setIsLatest(false);
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log(errorObject);
            alert("좋아요 클릭 에러!"+errorObject);
        })
    }
    const handleLikeRemoveClick = async (comment_id) => {
        // account_id는 나중에 바꿔야함
        console.log(comment_id);
        const data = {
            "account_id": 1,
            // "board_id": id
            "comment_id": comment_id
        }
        await axios.post('/like/remove/',
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data
            }
        ).then((res) => {
            alert("댓글에 좋아요를 취소했습니다");
            // setIsLatest(false);
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log(errorObject);
            alert("좋아요 클릭 에러!"+errorObject);
        })
    }

    const handleDeleteClick = async (comment_id) => {
        await axios.delete("/board/comment/" + comment_id)
            .then((res) => {
                alert("댓글을 지웠습니다");
                // setIsLatest(false);
                Refresh();
                console.log(res);

            }).catch(error => {
                alert("에러!");
                console.log(error);
            })
    }

    // const handleReCommentClick = () => {
    //     alert("답글쓰기 누름");
    // }

    // 일반 댓글
    const CommentComponent = (data) => {
        const [isShow, setIsShow] = useState(false);
        // let isShow = false;
        const handleReCommentClick = () =>{
            setIsShow(!isShow);
        }
        return(
            <div style={{marginBottom:'1rem'}} key={data.id}>
                <Card style={{borderRadius: '10px'}} className="text-left flex-row m-2" key={data.id}>
                    <img src={userImage} style={{height: "30px"}} className="ml-3 align-self-start mt-3"/>

                    <Card.Body>
                        {data.check_like?<span style={{float: "right", fontSize: '13px', color: '#FF6262'}}>
                                <img src={likeImage} onClick={()=>handleLikeRemoveClick(data.id)}
                                     style={{cursor: "pointer"}}/> {data.likes}</span>:
                            <span style={{float: "right", fontSize: '13px', color: '#949494'}}>
                                <img src={likeGrayImage} onClick={()=>handleLikeClick(data.id)}
                                     style={{cursor: "pointer"}}/> {data.likes}</span>}

                        <Card.Title className="mb-2" style={{fontSize: '14px'}}>{data.is_anonymous ? "익명" : data.writer}
                            <span style={{color: "#8C8C8C", fontSize: '12px', marginLeft: "10px"}}>
                                {dateFormat(data.created_at.substring(0, 16))}</span>
                        </Card.Title>
                        <Card.Text className="mb-0">

                            <span className={'delete-style'} onClick={() => handleDeleteClick(data.id)} style={{marginLeft:'0.5rem'}}>삭제</span>
                            <span className={'recomment-style'} onClick={() => handleReCommentClick()}>답글쓰기</span>
                            <p className=" mr-3 mb-1" style={{fontSize: '13px'}}>
                                {data.content}
                            </p>
                        </Card.Text>
                    </Card.Body>
                </Card>
                <WriteReComment
                    board_id={id}
                    Refresh={Refresh}
                    parent={data.id}
                    display={isShow}
                />
                <MakeReCommentList
                    boardReCommentData={data.sub_comment}
                    Refresh={Refresh}
                />
            </div>
        )
    }

    // 삭제된 댓글인데 대댓글이 있을 경우
    const RemovedCommentComponent = (data) => {
        return(
            <div style={{marginBottom:'1rem'}} key={data.id}>
                <Card style={{borderRadius: '10px'}} className="text-left flex-row m-2" key={data.id}>
                    <img src={userImage} style={{height: "30px"}} className="ml-3 align-self-start mt-3"/>

                    <Card.Body>

                        <Card.Text className="mb-0">
                            <p className=" mr-3 mb-1" style={{fontSize: '13px'}}>
                                삭제된 댓글입니다.
                            </p>
                        </Card.Text>
                    </Card.Body>
                </Card>
                <MakeReCommentList
                    boardReCommentData={data.sub_comment}
                    Refresh={Refresh}
                />
            </div>
        )
    }

    // if (loading) return <Loading/>;
    // if (error) return <div>에러가 발생했습니다{error.toString()}</div>;
    if (!boardCommentData) return null;
    if (Object.keys(boardCommentData).length == 0) return <div>댓글이 없습니다.</div>;

    return (
        <>
            {boardCommentData.map(data => (
                data.board_id != null ? CommentComponent(data) : RemovedCommentComponent(data)
            ))}
        </>
    );
}
