import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import searchImage from "../../icon/search_black.png";
import React, {useState} from "react";
import SelectButton from "../Button/SelectButton";
import MakeBoardList from "./MakeBoardList";
import {BlueButton} from "../Button/BlueButton";
import SubjectList from "./SubjectList";
import {useSelector} from "react-redux";

function BoardList({match}) {
    const [category, setCategory] = useState(0);
    const [selectedSubject, setSelectedSubject] = useState([]); // 과목별 게시판
    const [searchData, setSearchData] = useState(
        {
            search: 0,
            search_type: "select_title",
            keyword: ""
        });
    const {decoded} = useSelector((state) => state.user);

    window.scrollTo(0, 0);

    const handleSearchTextChange = (event) => {
        setSearchData(
            {
                ...searchData,
                keyword: event.target.value
            });
        if (event.target.value === "") {
            setSearchData({...searchData, keyword: "", search: 0});
        }
    }

    const handleSearchTypeChange = (event) => {
        setSearchData({...searchData, search_type: event.target.value})
    }

    const handleCategoryChange = (category_num) => {
        setCategory(category_num);

        // 검색 초기화
        setSearchData({is_search: false, search_type: "select_title", keyword: ""});

        // 과목별 게시판 선택한 과목 초기화
        setSelectedSubject([]);
    }

    const searchContents = () => {
        setSearchData({...searchData, search: searchData.search + 1})
        // console.log("서치 활성화");
    }

    const searchEnterPress = (e) => {
        // 검색 시 엔터를 눌렀을 때
        if (e.key === 'Enter') {
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
            <Title text='게시판' type='1'/>
            <Row style={{
                marginBottom: '1rem',
                marginTop: '2rem',
                alignItems: 'center',
                justifyContent: 'space-between',
                padding: '0px 10px'
            }}>
                <div>
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
                    <SelectButton
                        id={3} title='취업게시판' active={category}
                        onClick={() => handleCategoryChange(3)}
                    />
                </div>
                <div className="mr-2 ml-2 align-self-center">
                    <img src={searchImage} className={"search-icon"} onClick={searchContents}/>
                    <input type="text" value={searchData.keyword} onChange={handleSearchTextChange}
                           onKeyPress={searchEnterPress} className={"search-box"} placeholder={'검색'}/>
                    <select className={"search-type"} defaultValue={searchData.search_type}
                            onChange={handleSearchTypeChange}>
                        <option selected defaultValue="select_title">제목</option>
                        <option defaultValue="select_title_content">제목+내용</option>
                        <option defaultValue="select_writer">작성자</option>
                    </select>
                </div>
            </Row>

            {category === 2 ?
                <Row className="mb-3">
                    <Col>
                        <SubjectList
                            handleSelectSubject={handleSelectSubject}
                            handleRemoveSubject={handleRemoveSubject}
                        />

                    </Col>
                </Row>
                : null}

            <MakeBoardList
                category={category}
                match={match}
                searchData={searchData}
                setSearchData={setSearchData}
                selected_subject_list={selectedSubject}
            />

            {decoded ?
                <BlueButton match={match} type='newBoard' title="글쓰기"/>
                : null
            }
        </div>
    );
}

export default BoardList;
