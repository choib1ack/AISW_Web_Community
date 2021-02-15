import React, {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import userImage from "../../icon/user.svg";
import {ListButton} from "../ListButton";
import arrowImage from "../../icon/arrow.svg";
import sendImage from "../../icon/send.svg";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Button from "react-bootstrap/Button";
import {Checkbox} from "semantic-ui-react";
import Title from "../Title";
import fileImage from "../../icon/file.svg";
import axios from "axios";

export default function BoardDetail({match}) {
    const [boardDetailData, setBoardDetailData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const { board_category } = match.params;
    const { id } = match.params;
    const url = match.url;

    const Category = (c) => {
        switch (c) {
            case "free":
                return '자유게시판';
            case "qna":
                return "과목별게시판";
        }
    }

    // 첨부파일이 있을 때만 보여줌
    const AttachmentFile = (att) => {
        if(att == null) return null;
        return(
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
                // console.log(response.data);
                setBoardDetailData(response.data.data); // 데이터는 response.data 안에
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, []); // 여기 빈배열 안써주면 무한루프,,

    if (loading) return <tr><td colSpan={5}>로딩중..</td></tr>;
    if (error) return <tr><td colSpan={5}>에러가 발생했습니다{error.toString()}</td></tr>;
    if (!boardDetailData) return null;

    return (
        <div className="BoardDetail">
            <Container>
                <Title text='게시판' type='1'/>
                <div className="text-left mt-5 mb-4"
                     style={{borderTop: 'solid 2px #6CBACB', borderBottom: 'solid 2px #6CBACB'}}>
                    <div style={{
                        backgroundColor: "#EFF7F9",
                        paddingTop: '20px',
                        paddingLeft: '20px',
                        paddingBottom: '10px'
                    }}>
                        <p style={{color: "#6CBACB", fontSize: '14px'}} className="mb-1">{Category(board_category)}</p>
                        <p style={{fontSize: '18px'}} className="d-inline-block mr-1">{boardDetailData.title}</p>
                        {boardDetailData.attachment_file == null? "" : <img src={fileImage} className="d-inline-block"/>}
                        <div>
                            <p className="d-inline-block mr-3 mb-0" style={{color: "#8C8C8C", fontSize: '13px'}}>
                                {boardDetailData.is_anonymous? "익명" : boardDetailData.created_by}
                            </p>
                            <p className="d-inline-block mb-0" style={{color: "#8C8C8C", fontSize: '13px'}}>
                                {boardDetailData.created_at.substring(0, 10)} {boardDetailData.created_at.substring(11, 19)}
                            </p>
                        </div>
                    </div>

                    <div className="p-3">
                        <p>{boardDetailData.content}​</p>
                    </div>
                    {AttachmentFile(boardDetailData.attachment_file)}

                    <hr/>

                    <div className="p-3">
                        <Title text='댓글' type='2'/>
                        <CommentBox/>
                        <CommentBox/>
                        <ReplyBox/>
                        <ReplyBox/>
                        <CommentInput/>
                    </div>
                </div>
                <ListButton/>
            </Container>
        </div>
    )
}

export function CommentBox() {
    return (
        <Card style={{borderRadius: '10px'}} className="text-left flex-row m-2">
            <img src={userImage} style={{height: "30px"}} className="ml-3 align-self-start mt-3"/>

            <Card.Body>
                <Card.Title className="mb-0" style={{fontSize: '14px', fontWeight: 'bold'}}>익명1</Card.Title>
                <Card.Text className="mb-0">
                    <p className="d-inline-block mr-3 mb-3" style={{fontSize: '14px'}}>
                        네..
                    </p>
                </Card.Text>
                <Card.Subtitle style={{fontSize: '13px'}} className="text-muted">2021-01-09 11:10:05</Card.Subtitle>
            </Card.Body>
        </Card>
    )
}

export function ReplyBox() {
    return (
        <div style={{display: 'flex', flexDirection: 'row'}} className="ml-5">
            <img src={arrowImage} style={{height: "50px"}} className="ml-3 mt-3"/>
            <Card style={{borderRadius: '10px', backgroundColor: '#F9F9F9'}}
                  className="text-left flex-row m-2 border-0 w-100">
                <img src={userImage} style={{height: "30px"}} className="ml-3 align-self-start mt-3"/>

                <Card.Body>
                    <Card.Title className="mb-0" style={{fontSize: '14px', fontWeight: 'bold'}}>익명1</Card.Title>
                    <Card.Text className="mb-0">
                        <p className="d-inline-block mr-3 mb-3" style={{fontSize: '14px'}}>
                            네..
                        </p>
                    </Card.Text>
                    <Card.Subtitle style={{fontSize: '13px'}} className="text-muted">2021-01-09
                        11:10:05</Card.Subtitle>
                </Card.Body>
            </Card>
        </div>
    )
}

export function CommentInput() {
    return (
        <div className="mt-5 mb-3">
            <Checkbox label='익명' defaultChecked/>

            <InputGroup className="border-1" style={{borderRadius: '10px', border: 'solid 1px #E3E3E3'}}>
                <FormControl
                    style={{border: 'none', height: '100px', margin: '10px', verticalAlign: 'text-top', resize: 'none'}}
                    placeholder="댓글을 남겨보세요."
                    aria-describedby="basic-addon2"
                    as="textarea"
                />
                <InputGroup.Append>
                    <Button size="sm" variant="outline-secondary" className="border-0 m-3">
                        <img src={sendImage} style={{width: '20px'}} className="align-self-start"/>
                    </Button>
                </InputGroup.Append>
            </InputGroup>
        </div>

    )
}
