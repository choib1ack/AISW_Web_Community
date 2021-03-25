import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import searchImage from "../../icon/search_black.png";
import Table from "react-bootstrap/Table";
import Pagination from "../PaginationCustom";
import React, {useState} from "react";
import SelectButton from "../Button/SelectButton";
import MakeBoardList from "./MakeBoardList";
import {WriteButton} from "../Button/WriteButton";
import SubjectList from "./SubjectList";

function BoardList({match}) {
    const [category, setCategory] = useState(0);
    const [isSearch, setIsSearch] = useState(false);
    const [searchType, setSearchType] = useState("select_title");
    const [nowSearchText, setNowSearchText] = useState(null);
    const [searchText, setSearchText] = useState(null);
    // const [page, setPage] = useState(1);
    const [totalPage, setTotalPage] = useState(1);
    const [currentPage, setCurrentPage] = useState(0);
    const [selectedSubject, setSelectedSubject] = useState([]);


    window.scrollTo(0, 0);

    const handleSearchTextChange = (event) => {
        setNowSearchText(event.target.value);
        if (event.target.value == "") {
            setIsSearch(false);
        }
    }

    const handleSearchTypeChange = (event) => {
        setSearchType(event.target.value)
    }

    const handleCategoryChange = (category_num) => {
        setCategory(category_num);
        // 검색 초기화
        setSearchType("select_title");
        setNowSearchText("");
        setSearchText("");
        setIsSearch(false);
        // 페이징 초기화
        setCurrentPage(0);
        // 과목별게시판 카테고리 초기화
        setSelectedSubject([]);
    }

    const searchContents = () => {
        // 검색 시 실행
        setSearchText(nowSearchText);
        setIsSearch(true);
    }

    const searchEnterPress = (e) => {
        // 검색 시 엔터를 눌렀을 때
        if (e.key == 'Enter') {
            searchContents();
        }
    }

    const handleSelectSubject = (subject_name) => {
        setSelectedSubject([...selectedSubject, subject_name]);
    }
    const handleRemoveSubject = (subject_name) => {
        setSelectedSubject(selectedSubject.filter(subject => subject != subject_name));
    }

    return (
        <div className="Board">
            <Container>
                <Title text='게시판' type='1'/>
                <Row>
                    <Col>
                        <img src={searchImage} className={"search-icon"} onClick={searchContents}/>
                        <input type="text" value={nowSearchText} onChange={handleSearchTextChange}
                               onKeyPress={searchEnterPress} className={"search-box"} placeholder={'검색'}/>
                        <select className={"search-type"} value={searchType} onChange={handleSearchTypeChange}>
                            <option selected value="select_title">제목</option>
                            <option value="select_title_content">제목+내용</option>
                            <option value="select_writer">작성자</option>
                        </select>
                    </Col>
                </Row>

                <Row style={{marginTop: '2rem'}}>
                    <Col lg={12} md={12} sm={12}>
                        <SelectButton
                            id={0} title='전체' active={category}
                            onClick={() => handleCategoryChange(0)}
                        />
                        <SelectButton
                            id={1} title='자유게시판' active={category}
                            onClick={() => handleCategoryChange(1)}
                        />
                        <SelectButton
                            id={2} title='과목별게시판' active={category}
                            onClick={() => handleCategoryChange(2)}
                        />
                        {/*<DropdownButton/>*/}
                    </Col>
                </Row>

                <Row style={{marginBottom: '1rem'}}>
                    <Col lg={12} md={12} sm={12}>
                        {category==2? <SubjectList
                            handleSelectSubject={handleSelectSubject}
                            handleRemoveSubject={handleRemoveSubject}
                        />:null}
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
                    <MakeBoardList
                        category={category}
                        match={match}
                        search_type={searchType}
                        search_text={searchText}
                        is_search={isSearch}
                        setIsSearch={setIsSearch}
                        setNowSearchText={setNowSearchText}
                        setTotalPage={setTotalPage}
                        current_page={currentPage}
                        selected_subject_list={selectedSubject}
                    />

                    </tbody>
                </Table>
                <WriteButton match={match} type={'newBoard'}/>

                <Pagination
                    total_pages={totalPage}
                    current_page={currentPage}
                    setCurrentPage={setCurrentPage}
                />
            </Container>
        </div>
    );
}

export default BoardList;

// function DropdownButton() {
//     const [selectedOption, setSelectedOption] = useState(null);
//
//     const options = [
//         {value: 'OS', label: '운영체제'},
//         {value: 'Network', label: '네트워크'},
//         {value: 'Java', label: '객체지향 프로그래밍'},
//         {value: 'Android', label: '안드로이드 프로그래밍'},
//         {value: 'SE', label: '소프트웨어 공학'},
//     ];
//
//     return (
//         <Select
//             defaultValue={[]}
//             isMulti
//             name="subjects"
//             options={options}
//             className="basic-multi-select"
//             classNamePrefix="select"
//             style={{width:'300px'}}
//             theme={theme => ({
//                 ...theme,
//                 borderRadius: 0,
//                 width: '300px',
//                 colors: {
//                     ...theme.colors,
//                     primary50: '#F5F5F5',
//                     primary25: '#EFF7F9',
//                     primary: '#6CBACB',
//                 },
//             })}
//         />
//
//     );
//
// }