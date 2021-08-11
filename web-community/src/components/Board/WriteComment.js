import React, {useState} from "react";
import {Checkbox} from "semantic-ui-react";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import sendImage from "../../icon/send.svg";
import axiosApi from "../../axiosApi";

export default function WriteComment({board_id, board_category, Refresh}) {
    const [commentText, setCommentText] = useState("");
    const [anonymousState, setAnonymousState] = useState(true);
    const auth_url = (board_category === 'qna' ? 'auth-student' : 'auth');

    const handleCommentSend = async () => {
        if (commentText.trim() === '') return;

        // user_id는 나중에 바꿔야함
        const data = {
            "board_id": Number(board_id),
            "content": commentText.trim(),
            "is_anonymous": anonymousState,
        }

        await axiosApi.post(`/${auth_url}/${board_category}/${board_id}/comment`,
            {data: data}
        ).then((res) => {
            setCommentText("");
            alert("댓글을 달았어요!");
            Refresh();
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            alert("에러!" + errorObject);
        })
    }

    const handleTextChange = (event) => {
        setCommentText(event.target.value);
    }
    const handleAnonymousCheck = () => {
        setAnonymousState(!anonymousState);
    }

    const searchEnterPress = (e) => { // 글쓸때 줄바꿈이랑 겹쳐서 일단 보류
        // 검색 시 엔터를 눌렀을 때
        if (e.key === 'Enter') {
            handleCommentSend();
        }
    }

    return (
        <div className="mt-5 mb-3">
            {/*<Checkbox label='익명' checked={anonymousState}/>*/}
            <Checkbox label='익명' checked={anonymousState} onChange={handleAnonymousCheck}/>
            <InputGroup className="border-1" style={{borderRadius: '10px', border: 'solid 1px #E3E3E3'}}>
                <FormControl
                    style={{
                        border: 'none',
                        boxShadow: 'none',
                        height: '100px',
                        margin: '10px',
                        verticalAlign: 'text-top',
                        resize: 'none'
                    }}
                    placeholder="댓글을 남겨보세요."
                    aria-describedby="basic-addon2"
                    as="textarea"
                    value={commentText}
                    // onKeyPress={searchEnterPress}
                    onChange={handleTextChange}
                />
                <InputGroup.Append>
                    <img src={sendImage} style={{width: '20px', cursor: 'pointer', margin: 'auto 30px'}}
                         className="align-self-start" onClick={handleCommentSend}/>
                </InputGroup.Append>
            </InputGroup>
        </div>

    )
}

