import React, {useEffect, useState} from "react";
import Card from "react-bootstrap/Card";
import userImage from "../../icon/user.svg";
import likeImage from "../../icon/like.svg";
import likeGrayImage from "../../icon/like_gray.svg";
import axiosApi from "../../axiosApi";

export default function MakeReCommentList({board_id, board_category, Refresh, boardReCommentData}) {
    const auth_url = (board_category === 'qna' ? 'auth-student' : 'auth');

    const dateFormat = (s) => {
        let result = "";
        result += s.substring(5, 7) + "/" + s.substring(8, 10) + "  " + s.substring(11, 16);
        return result;
    }

    const handleLikeClick = async (comment_id) => {
        const data = {
            "comment_id": comment_id
        }

        await axiosApi.post('/auth/like/press/',
            {data: data}
        ).then((res) => {
            alert("댓글에 좋아요를 눌렀습니다");
            // setIsLatest(false);
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            alert("에러!" + errorObject);
        })
    }

    const handleLikeRemoveClick = async (comment_id) => {
        await axiosApi.delete(`/auth/like/remove/${comment_id}?target=COMMENT`
        ).then((res) => {
            alert("댓글에 좋아요를 취소했습니다");
            // setIsLatest(false);
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log(errorObject);
        })
    }

    const handleDeleteClick = async (comment_id) => {
        console.log(board_id);

        await axiosApi.delete(`${auth_url}/${board_category}/${board_id}/comment/${comment_id}`)
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

    if (!boardReCommentData) return null;
    if (Object.keys(boardReCommentData).length === 0) return null;

    return (
        <>
            {boardReCommentData.map(data => (
                <div key={data.id}>
                    <div style={{display: 'flex', flexDirection: 'row'}} className="ml-5">
                        {/*<img src={arrowImage} style={{height: "20px", opacity:'0.7'}} className="ml-3 mt-3"/>*/}
                        <Card style={{borderRadius: '10px', backgroundColor: '#F9F9F9'}}
                              className="text-left flex-row m-2 border-0 w-100">
                            <img src={userImage} style={{height: "30px"}} className="ml-3 align-self-start mt-3"/>

                            <Card.Body>
                                {data.check_like ? <span style={{float: "right", fontSize: '13px', color: '#FF6262'}}>
                                <img src={likeImage} onClick={() => handleLikeRemoveClick(data.id)}
                                     style={{cursor: "pointer"}}/> {data.likes}</span> :
                                    <span style={{float: "right", fontSize: '13px', color: '#949494'}}>
                                <img src={likeGrayImage} onClick={() => handleLikeClick(data.id)}
                                     style={{cursor: "pointer"}}/> {data.likes}</span>}

                                <Card.Title className="mb-2"
                                            style={{fontSize: '14px'}}>{data.writer}
                                    <span style={{
                                        color: "#8C8C8C",
                                        fontSize: '12px',
                                        marginLeft: "10px"
                                    }}>{dateFormat(data.created_at.substring(0, 16))}</span>
                                </Card.Title>
                                <Card.Text className="mb-0">
                                    <span className={'delete-style'} onClick={() => handleDeleteClick(data.id)}
                                          style={{marginLeft: '0.5rem'}}>삭제</span>
                                    <p className="d-inline-block mr-3 mb-1" style={{fontSize: '14px'}}>
                                        {data.content}
                                    </p>
                                </Card.Text>

                            </Card.Body>
                        </Card>
                    </div>
                </div>
            ))}
        </>
    );
}
