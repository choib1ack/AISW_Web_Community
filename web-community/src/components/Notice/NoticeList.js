import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import searchImage from "../../icon/search_black.png";
import Table from "react-bootstrap/Table";
import Pagination from "../PaginationCustom";
import React, {useState} from "react";
import SelectButton from "../SelectButton";
import MakeNoticeList from "./MakeNoticeList"
import {WriteButton} from "../WriteButton";

export default function NoticeList({match}) {

    const [category, setCategory] = useState(0);
    const [isSearch, setIsSearch] = useState(false); // 0:제목, 1:제목+내용, 2:작성자
    const [searchType, setSearchType] = useState("select_title"); // 0:제목, 1:제목+내용, 2:작성자
    const [searchText, setSearchText] = useState(null); // 0:제목, 1:제목+내용, 2:작성자
    const [page, setPage] = useState(1);

    const handleSearchTextChange = (event) =>{
        setSearchText(event.target.value);
        if(event.target.value==""){
            setIsSearch(false);
            console.log("텍스트 없음");
        }
        console.log("텍스트 변경됨 : "+event.target.value);
    }
    const handleSearchTypeChange = (event) =>{
        setSearchType(event.target.value)
        console.log("서치 타입 변경됨 : "+event.target.value);
    }

    const handleCategoryChange = (category_num) =>{
        setCategory(category_num);
        // 검색 초기화
        setSearchType("select_title");
        setSearchText("");
    }

    const searchContents = () => {
        // 검색 시 실행
        setIsSearch(true);
        console.log("서치 활성화");
    }

    const searchEnterPress = (e) => {
        // 검색 시 엔터를 눌렀을 때
        if (e.key == 'Enter') {
            console.log("엔터누름");
            searchContents();
        }
    }

    return (
        <div className="Notice">
            <Container>
                <Title text='공지사항' type='1'/>
                <Row style={{marginBottom: '1rem', marginTop: '2rem'}}>
                    <Col lg={6} md={8} sm={12}>
                        <SelectButton id='0' title='전체' active={category}
                                      onClick={()=>handleCategoryChange(0)}/>
                        <SelectButton id='1' title='학교 홈페이지' active={category}
                                      onClick={()=>handleCategoryChange(1)}/>
                        <SelectButton id='2' title='학과사무실' active={category}
                                      onClick={()=>handleCategoryChange(2)}/>
                        <SelectButton id='3' title='학생회' active={category}
                                      onClick={()=>handleCategoryChange(3)}/>
                    </Col>
                    <Col lg={6} md={4} sm={12}>
                        <img src={searchImage} className={"search-icon"} onClick={searchContents}/>
                        <input type="text" value={searchText} onChange={handleSearchTextChange} onKeyPress={searchEnterPress} className={"search-box"} placeholder={'검색'}/>
                        <select className={"search-type"} value={searchType} onChange={handleSearchTypeChange}>
                            <option selected value="select_title" >제목</option>
                            <option value="select_title_content" >제목+내용</option>
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
                        search_type={searchType}
                        search_text={searchText}
                        is_search={isSearch}
                    />
                    </tbody>
                </Table>

                <WriteButton match={match} type={'newNotice'}/>

                <Pagination active={1}/>
            </Container>
        </div>
    );
}



