import React, {useState} from "react";
import {Checkbox} from "semantic-ui-react";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import sendImage from "../../icon/send.svg";
import Card from "react-bootstrap/Card";
import axiosApi from "../../axiosApi";


export default function WriteReComment({board_id, board_category, Refresh, display, parent}) {
    const [reCommentText, setReCommentText] = useState("");
    const [anonymousState, setAnonymousState] = useState(true);
    const auth_url = (board_category === 'qna' ? 'auth-student' : 'auth');

    const handleCommentSend = async () => {
        if (reCommentText.trim() === '') return;

        // account_id는 나중에 바꿔야함
        const data = {
            "board_id": board_id,
            "content": reCommentText.trim(),
            "is_anonymous": anonymousState,
            "super_comment_id": parent
        }

        await axiosApi.post(`/${auth_url}/${board_category}/${board_id}/comment`,
            {data: data}
        ).then((res) => {
            setReCommentText("");
            alert("대댓글을 달았어요!");
            Refresh();
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            alert("에러!" + errorObject);
        })
    }

    const handleTextChange = (event) => {
        setReCommentText(event.target.value);
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

    if (!display) return null;

    return (
        <div style={{display: 'flex', flexDirection: 'row'}} className="ml-5">
            {/*<img src={arrowImage} style={{height: "20px", opacity:'0.7'}} className="ml-3 mt-3"/>*/}
            <div style={{width: '100%', margin: '0.5rem'}}>
                <Checkbox label='익명' checked={anonymousState} onChange={handleAnonymousCheck}/>

                <Card style={{borderRadius: '10px'}}
                      className="text-left flex-row border-0 w-100">

                    <InputGroup className="border-1"
                                style={{borderRadius: '10px', border: 'solid 1px #E3E3E3', width: '100%'}}>

                        <FormControl
                            style={{
                                border: 'none',
                                boxShadow: 'none',
                                height: '40px',
                                margin: '10px',
                                verticalAlign: 'text-top',
                                resize: 'none'
                            }}
                            placeholder="대댓글을 남겨보세요."
                            aria-describedby="basic-addon2"
                            as="textarea"
                            value={reCommentText}
                            // onKeyPress={searchEnterPress}
                            onChange={handleTextChange}
                        />
                        <InputGroup.Append>
                            <img src={sendImage} style={{width: '20px', cursor: 'pointer', margin: 'auto 30px'}}
                                 className="align-self-start" onClick={handleCommentSend}/>
                        </InputGroup.Append>
                    </InputGroup>
                </Card>
            </div>
        </div>
    )
}
