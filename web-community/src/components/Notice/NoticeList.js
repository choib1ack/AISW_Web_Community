import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import searchImage from "../../icon/search_black.png";
import Table from "react-bootstrap/Table";
import Pagination from "../PaginationCustom";
import React, {useState} from "react";
import MakeNoticeList from "./MakeNoticeList"
import SelectButton from "../Button/SelectButton";
import {BlueButton} from "../Button/BlueButton";

export default function NoticeList({match}) {
    const [category, setCategory] = useState(0);
    const [searchData, setSearchData] = useState({is_search:false, search_type:"select_title", keyword:""});
    const [pageInfo, setPageInfo] = useState({current:0, total:1});

    window.scrollTo(0, 0);

    const handleSearchTextChange = (event) => {
        setSearchData({...searchData, keyword:event.target.value});
        if (event.target.value === "") {
            setSearchData({...searchData, keyword:"", is_search:false});
        }
    }
    const handleSearchTypeChange = (event) => {
        setSearchData({...searchData, search_type:event.target.value})
    }

    const handleCategoryChange = (category_num) => {
        setCategory(category_num);

        // 검색 초기화
        setSearchData({is_search:false, search_type:"select_title", keyword:""});

        // 페이징 초기화
        setPageInfo({...pageInfo, current: 0});
    }

    const searchContents = () => {
        setSearchData({...searchData, is_search:true})
        // console.log("서치 활성화");
    }

    const searchEnterPress = (e) => {
        // 검색 시 엔터를 눌렀을 때
        if (e.key === 'Enter') {
            searchContents();
        }
    }

    return (
        <div className="Notice">
            <Container>
                <Title text='공지사항' type='1'/>
                <Row style={{marginBottom: '1rem', marginTop: '2rem', alignItems: 'center'}}>
                    <Col lg={6} md={6} sm={12}>
                        <SelectButton id={0} title='전체' active={category}
                                      onClick={() => handleCategoryChange(0)}/>
                        <SelectButton id={1} title='학교 홈페이지' active={category}
                                      onClick={() => handleCategoryChange(1)}/>
                        <SelectButton id={2} title='학과사무실' active={category}
                                      onClick={() => handleCategoryChange(2)}/>
                        <SelectButton id={3} title='학생회' active={category}
                                      onClick={() => handleCategoryChange(3)}/>
                    </Col>
                    <Col lg={6} md={6} sm={12}>
                        <img src={searchImage} className={"search-icon"} onClick={searchContents}/>
                        <input type="text" value={searchData.keyword} onChange={handleSearchTextChange}
                               onKeyPress={searchEnterPress} className={"search-box"} placeholder={'검색'}/>
                        <select className={"search-type"} value={searchData.search_type} onChange={handleSearchTypeChange}>
                            <option value="select_title">제목</option>
                            <option value="select_title_content">제목+내용</option>
                            <option value="select_writer">작성자</option>
                        </select>
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
                        match={match}
                        searchData={searchData}
                        setSearchData={setSearchData}
                        pageInfo={pageInfo}
                        setPageInfo={setPageInfo}
                    />
                    </tbody>
                </Table>

                <BlueButton match={match} type={'newNotice'} title="글쓰기"/>

                <Pagination
                    pageInfo={pageInfo}
                    setPageInfo={setPageInfo}/>

            </Container>
        </div>
    );
}



