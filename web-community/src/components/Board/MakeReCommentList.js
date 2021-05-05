import React, {useEffect, useState} from "react";
import axios from "axios";
import Card from "react-bootstrap/Card";
import userImage from "../../icon/user.svg";
import likeImage from "../../icon/like.svg";
import Loading from "../Loading";
import arrowImage from "../../icon/comment_replay.png";
import likeGrayImage from "../../icon/like_gray.svg";

export default function MakeReCommentList({Refresh, boardReCommentData}) {

    const dateFormat = (s) => {
        let result = "";
        result += s.substring(5, 7) + "/" + s.substring(8, 10) + "  " + s.substring(11, 16);
        return result;
    }

    const handleLikeClick = async (comment_id) => {
        // account_id는 나중에 바꿔야함
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
            alert("에러!"+errorObject);
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

    if (!boardReCommentData) return null;
    if (Object.keys(boardReCommentData).length == 0) return null;

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
                            {data.check_like?<span style={{float: "right", fontSize: '13px', color: '#FF6262'}}>
                                <img src={likeImage} onClick={()=>handleDeleteClick(data.id)}
                                     style={{cursor: "pointer"}}/> {data.likes}</span>:
                                <span style={{float: "right", fontSize: '13px', color: '#949494'}}>
                                <img src={likeGrayImage} onClick={()=>handleLikeClick(data.id)}
                                     style={{cursor: "pointer"}}/> {data.likes}</span>}

                            <Card.Title className="mb-2" style={{fontSize: '14px'}}>{data.is_anonymous ? "익명" : data.writer}
                            <span style={{color: "#8C8C8C", fontSize: '12px', marginLeft: "10px"}}>{dateFormat(data.created_at.substring(0, 16))}</span>
                            </Card.Title>
                            <Card.Text className="mb-0">
                                <span className={'delete-style'} onClick={() => handleDeleteClick(data.id)} style={{marginLeft:'0.5rem'}}>삭제</span>
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
