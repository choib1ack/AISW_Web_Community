import React from "react";
import Container from "react-bootstrap/Container";
import Table from "react-bootstrap/Table";
import fileImage from "../icon/file.svg";
import photoImage from "../icon/photo.svg";
import searchImage from "../icon/search.svg";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from 'classnames';
import Pagination from "react-bootstrap/Pagination";

function Board() {
    let active = 2;
    let items = [];
    for (let number = 1; number <= 5; number++) {
        items.push(
            <Pagination.Item key={number} active={number === active}>
                {number}
            </Pagination.Item>,
        );
    }
    return (
        <div className="Board">
            <Container >
                <p className={"title"}>게시판</p>
                <Row style={{marginBottom: '1rem', marginTop: '2rem'}}>
                    <Col lg={6} md={8} sm={12}>
                        <Button className={classNames("select-btn", "on")}>전체</Button>
                        <Button className={classNames("select-btn", "off")}>자유게시판</Button>
                        <Button className={classNames("select-btn", "off")}>과목별게시판</Button>
                    </Col>
                    <Col lg={6} md={4} sm={12}>
                        <img src={searchImage} style={{float: "right", marginLeft:"10px", height:"25px"}} />
                        <input type="text" className={"search-box"} placeholder={'검색'}/>
                        {/*style={{background:`url(${searchImage})`, backgroundRepeat:'no-repeat'}}>*/}

                    </Col>
                </Row>
                {/*table 내 내용은 데이터에 맞게 가져오도록 처리 필요*/}
                <Table>
                    <thead>
                    <tr>
                        <th style={{width: "10%"}}>no</th>
                        <th style={{width: "55%"}}>제목</th>
                        <th style={{width: "10%"}}>작성자</th>
                        <th style={{width: "10%"}}>등록일</th>
                        <th style={{width: "10%"}}>조회</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>10</td>
                        <td>[멀티캠퍼스] 코딩테스트 대회 안내 파일</td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>9</td>
                        <td>AI·소프트웨어학부 플러스친구 안내
                            <img src={photoImage} style={{marginLeft: '5px'}}/>
                        </td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>산학과제 배정 공고</td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>ICT학점연계프로젝트인턴십 사업 안내</td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td>21학번 새내기 단톡방 개설 안내</td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>AI·소프트웨어학부 플러스친구 안내
                            <img src={photoImage} style={{marginLeft: '5px'}}/>
                        </td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>ICT학점연계프로젝트인턴십 사업 안내</td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>산학과제 배정 공고</td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>ICT학점연계프로젝트인턴십 사업 안내</td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>[멀티캠퍼스] 코딩테스트 대회 안내 파일
                            <img src={fileImage} style={{marginLeft: '5px'}}/>
                        </td>
                        <td>양희림</td>
                        <td>2021-01-16</td>
                        <td>1</td>
                    </tr>
                    </tbody>
                </Table>
                <Row>
                    <Col lg={12} md={12} sm={12}>
                        <Button className={classNames("select-btn", "on")}
                                style={{float:'right'}}>글쓰기</Button>
                    </Col>
                </Row>
                <Pagination size="sm" className="align-self-center justify-content-center" style={{marginBottom: '3rem'}}>{items}</Pagination>
            </Container>
        </div>
    );
}

export default Board;

