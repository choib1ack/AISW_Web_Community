import React, {useEffect, useReducer, useState} from "react";
import Card from "react-bootstrap/Card";
import userImage from "../../icon/user.svg";
import likeImage from "../../icon/like.svg";
import likeGrayImage from "../../icon/like_gray.svg"
import WriteReComment from "./WriteReComment";
import MakeReCommentList from "./MakeReCommentList";
import axiosApi from "../../axiosApi";

export default function MakeCommentList({id, board_category, Refresh, board_comment_data}) {
    const [boardCommentData, setBoardCommentData] = useState(board_comment_data);
    const auth_url = (board_category === 'qna' ? 'auth-student' : 'auth');

    const dateFormat = (s) => {
        let result = "";
        result += s.substring(5, 7) + "/" + s.substring(8, 10) + "  " + s.substring(11, 16);
        return result;
    }

    const handleDeleteClick = async (comment_id) => {
        await axiosApi.delete(`${auth_url}/${board_category}/${id}/comment/${comment_id}`)
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

    // 일반 댓글
    const CommentComponent = (data) => {
        const [isShow, setIsShow] = useState(false);
        const [likeState, dispatch] = useReducer(reducer, {"press": data.check_like, "num": data.likes});

        // let isShow = false;
        const handleReCommentClick = () => {
            setIsShow(!isShow);
        }

        const handleLikeClick = async (comment_id) => {
            const data = {
                "comment_id": comment_id
            }

            await axiosApi.post('/like/press',
                {data: data}
            ).then((res) => {
                // alert("댓글에 좋아요를 눌렀습니다");
                dispatch({type: 'PRESS'});
            }).catch(error => {
                let errorObject = JSON.parse(JSON.stringify(error));
                // console.log(errorObject);
                alert("좋아요 클릭 에러!" + errorObject);
            })
        }

        const handleLikeCancelClick = async (comment_id) => {
            await axiosApi.delete(`/like/remove/${comment_id}?target=COMMENT`
            ).then((res) => {
                // alert("댓글에 좋아요를 취소했습니다");
                dispatch({type: 'REMOVE'});
            }).catch(error => {
                let errorObject = JSON.parse(JSON.stringify(error));
                // console.log(errorObject);
                alert("좋아요 클릭 에러!" + errorObject);
            })
        }

        function reducer(state, action) {
            switch (action.type) {
                case 'INITIALIZE':
                    console.log("초기화!");
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

        return (
            <div style={{marginBottom: '1rem'}} key={data.id}>
                <Card style={{borderRadius: '10px'}} className="text-left flex-row m-2" key={data.id}>
                    <img src={userImage} style={{height: "30px"}} className="ml-3 align-self-start mt-3"/>

                    <Card.Body>
                        {likeState.press ? <span style={{float: "right", fontSize: '13px', color: '#FF6262'}}>
                                <img src={likeImage} onClick={() => handleLikeCancelClick(data.id)}
                                     style={{cursor: "pointer"}}/> {likeState.num}</span> :
                            <span style={{float: "right", fontSize: '13px', color: '#949494'}}>
                                <img src={likeGrayImage} onClick={() => handleLikeClick(data.id)}
                                     style={{cursor: "pointer"}}/> {likeState.num}</span>}

                        <Card.Title className="mb-2" style={{fontSize: '14px'}}>{data.is_anonymous ? "익명" : data.writer}
                            <span style={{color: "#8C8C8C", fontSize: '12px', marginLeft: "10px"}}>
                                {dateFormat(data.created_at.substring(0, 16))}</span>
                        </Card.Title>
                        <Card.Text className="mb-0">

                            <span className={'delete-style'} onClick={() => handleDeleteClick(data.id)}
                                  style={{marginLeft: '0.5rem'}}>삭제</span>
                            <span className={'recomment-style'} onClick={() => handleReCommentClick()}>답글쓰기</span>
                            <p className=" mr-3 mb-1" style={{fontSize: '13px'}}>
                                {data.content}
                            </p>
                        </Card.Text>
                    </Card.Body>
                </Card>
                <WriteReComment
                    board_id={id}
                    board_category={board_category}
                    Refresh={Refresh}
                    parent={data.id}
                    display={isShow}
                />
                <MakeReCommentList
                    board_id={id}
                    board_category={board_category}
                    boardReCommentData={data.sub_comment}
                    Refresh={Refresh}
                />
            </div>
        )
    }

    // 삭제된 댓글인데 대댓글이 있을 경우
    const RemovedCommentComponent = (data) => {
        return (
            <div style={{marginBottom: '1rem'}} key={data.id}>
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
                    board_id={id}
                    board_category={board_category}
                    boardReCommentData={data.sub_comment}
                    Refresh={Refresh}
                />
            </div>
        )
    }

    // if (loading) return <Loading/>;
    // if (error) return <div>에러가 발생했습니다{error.toString()}</div>;
    if (!boardCommentData) return null;
    if (Object.keys(boardCommentData).length === 0) return <div>댓글이 없습니다.</div>;

    return (
        <>
            {boardCommentData.map(data => (
                data.board_id != null ? CommentComponent(data) : RemovedCommentComponent(data)
            ))}
        </>
    );
}
