import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import searchImage from "../../icon/search.svg";
import Table from "react-bootstrap/Table";
import Pagination from "../PaginationCustom";
import React, {useState} from "react";
import SelectButton from "../SelectButton";
import MakeNoticeList from "./MakeNoticeList"
import {WriteButton} from "../WriteButton";

export default function NoticeList({match}) {

    const [category, setCategory] = useState(0);
    const [page, setPage] = useState(1);

    return (
        <div className="Notice">
            <Container>
                <Title text='공지사항' type='1'/>
                <Row style={{marginBottom: '1rem', marginTop: '2rem'}}>
                    <Col lg={6} md={8} sm={12}>
                        <SelectButton id='0' title='전체' active={category}
                                      onClick={()=>setCategory(0)}/>
                        <SelectButton id='1' title='학교 홈페이지' active={category}
                                      onClick={()=>setCategory(1)}/>
                        <SelectButton id='2' title='학과사무실' active={category}
                                      onClick={()=>setCategory(2)}/>
                        <SelectButton id='3' title='학생회' active={category}
                                      onClick={()=>setCategory(3)}/>
                    </Col>
                    <Col lg={6} md={4} sm={12}>
                        <img src={searchImage} style={{float: "right", marginLeft: "10px", height: "25px"}}/>
                        <input type="text" className={"search-box"} placeholder={'검색'}/>

                    </Col>
                </Row>
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
                    <MakeNoticeList
                        category={category}
                        match={match}/>
                    </tbody>
                </Table>

                <WriteButton match={match} type={'newNotice'}/>

                <Pagination active={1}/>
            </Container>
        </div>
    );
}



