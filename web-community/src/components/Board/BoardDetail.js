import React from "react";
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

export default function BoardDetail() {
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
                        <p style={{color: "#6CBACB", fontSize: '14px'}} className="mb-1">자유게시판></p>
                        <p style={{fontSize: '18px'}} className="d-inline-block mr-1">졸업작품 안하면 졸업 안되나요?</p>

                        <div>
                            <p className="d-inline-block mr-3 mb-0" style={{color: "#8C8C8C", fontSize: '13px'}}>익명</p>
                            <p className="d-inline-block mb-0" style={{color: "#8C8C8C", fontSize: '13px'}}>2021-01-09
                                11:10:05</p>
                        </div>
                    </div>

                    <div className="p-3">
                        <p>졸업작품 꼭 해야 졸업 가능한건가요?ㅠ 지금 3학년 올라가면 팀원들한테 피해만 끼칠거같아서요ㅠㅠ​</p>
                    </div>

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
