import React, {useState} from "react";
import axios from "axios";
import {Checkbox} from "semantic-ui-react";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Button from "react-bootstrap/Button";
import sendImage from "../../icon/send.svg";

export default function WriteComment({board_id, Refresh}) {
    const [commentText, setCommentText] = useState("");
    const [anonymousState, setAnonymousState] = useState(true);

    const handleCommentSend = async () => {
        if(commentText.trim()=='') return;
        // user_id는 나중에 바꿔야함
        const data = {
            "content": commentText.trim(),
            "is_anonymous": anonymousState,
            "board_id": board_id,
            "account_id": 1
        }
        await axios.post("/board/comment",
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data
            }
        ).then((res) => {
            setCommentText("");
            alert("댓글을 달았어요!");
            Refresh();
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            alert("에러!"+errorObject);
        })
    }

    const handleTextChange = (event) =>{
        setCommentText(event.target.value);
    }
    const handleAnonymousCheck = () =>{
        setAnonymousState(!anonymousState);
    }

    const searchEnterPress = (e) => { // 글쓸때 줄바꿈이랑 겹쳐서 일단 보류
        // 검색 시 엔터를 눌렀을 때
        if (e.key == 'Enter') {
            console.log("엔터누름");
            handleCommentSend();
        }
    }
    return (
        <div className="mt-5 mb-3">
            {/*<Checkbox label='익명' checked={anonymousState}/>*/}
            <Checkbox label='익명' checked={anonymousState} onChange={handleAnonymousCheck}/>
            <InputGroup className="border-1" style={{borderRadius: '10px', border: 'solid 1px #E3E3E3'}}>
                <FormControl
                    style={{border: 'none', boxShadow:'none', height: '100px', margin: '10px', verticalAlign: 'text-top', resize: 'none'}}
                    placeholder="댓글을 남겨보세요."
                    aria-describedby="basic-addon2"
                    as="textarea"
                    value={commentText}
                    // onKeyPress={searchEnterPress}
                    onChange={handleTextChange}
                />
                <InputGroup.Append>
                    <img src={sendImage} style={{width: '20px', cursor:'pointer', margin:'auto 30px'}} className="align-self-start" onClick={handleCommentSend}/>
                </InputGroup.Append>
            </InputGroup>
        </div>

    )
}

