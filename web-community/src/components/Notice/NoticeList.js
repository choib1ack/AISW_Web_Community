import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import searchImage from "../../icon/search.svg";
import Table from "react-bootstrap/Table";
import photoImage from "../../icon/photo.svg";
import fileImage from "../../icon/file.svg";
import Pagination from "../PaginationCustom";
import React, {useState} from "react";
import {Link} from "react-router-dom";
import noticeData from "./notice-list.json";
import axios from 'axios';
import SelectButton from "../SelectButton";

export default function NoticeList({match}) {

    const [category, setCategory] = useState(0);

    let lists; // 글 리스트
    let data;

    const request = async (category, page) => {
        setCategory(category);
        let url = "/notice"
        switch (category) {
            case 1: url+="/university"; break;
            case 2: url+="/department"; break;
            case 3: url+="/council"; break;
        }
        url+="?page="+page;
        const response = await axios.get(url);
        console.log(response.data);
        lists = () => makeList(match, data);
    }

    return (
        <div className="Notice">
            <Container>
                <Title text='공지사항' type='1'/>
                <Row style={{marginBottom: '1rem', marginTop: '2rem'}}>
                    <Col lg={6} md={8} sm={12}>
                        <SelectButton id='0' title='전체' active={category}
                                      onClick={() => request(0, "1")}/>
                        <SelectButton id='1' title='학교 홈페이지' active={category}
                                      onClick={() => request(1, "1")}/>
                        <SelectButton id='2' title='학과사무실' active={category}
                                      onClick={() => request(2, "1")}/>
                        <SelectButton id='3' title='학생회' active={category}
                                      onClick={() => request(3, "1")}/>
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
                    {lists = () => request(1, "1")}

                    </tbody>
                </Table>
                <Pagination active={1}/>
            </Container>
        </div>
    );
}

function makeList(match, data) {
    let noticeData = data;
    let status = (status) =>{
        switch (status) {
            case 0:
                return "긴급";
            case 1:
                return "상단 고정";
            case 2:
                return "일반";
        }
    }

    let lists = [];
    for (let i = 0; i < Object.keys(noticeData.data).length; i++) {
        lists.push(
            <tr key={noticeData.data[i].id}>
                <td>{()=>status(noticeData.data[i].status)}</td>
                <td>
                    <Link to={`${match.url}/${noticeData.data[i].id}`} style={{color: 'black'}}>
                        {noticeData.data[i].title}
                    </Link>
                </td>
                <td>가천대학교</td>
                <td>{noticeData.data[i].created_at}</td>
                <td>{noticeData.data[i].attachment_file != null ? 1 : 0}</td>
            </tr>
        );
    }

    return lists;
}
