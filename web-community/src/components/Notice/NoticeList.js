import Title from "../Title";
import Row from "react-bootstrap/Row";
import searchImage from "../../icon/search_black.png";
import React, {useState} from "react";
import MakeNoticeList from "./MakeNoticeList"
import SelectButton from "../Button/SelectButton";
import {BlueButton} from "../Button/BlueButton";
import {NOTICE_WRITE_ROLE} from "../../constants";
import {useDispatch, useSelector} from "react-redux";
import {setActiveTab} from "../../features/menuSlice";

export default function NoticeList({match}) {
    const [category, setCategory] = useState(!match.params.notice_category? 0 : parseInt(match.params.notice_category));
    const [searchData, setSearchData] = useState(
        {
            search: 0,
            search_type: "select_title",
            keyword: ""
        });
    const {decoded} = useSelector((state) => state.user);
    const active_change_dispatch = useDispatch();
    active_change_dispatch(setActiveTab(1));

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
        setSearchData({search: 0, search_type: "select_title", keyword: ""});
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

    return (
        <div className="Notice">
            <Title text='공지사항' type='1'/>
            <Row style={{
                marginBottom: '1rem',
                marginTop: '2rem',
                alignItems: 'center',
                justifyContent: 'space-between',
                padding: '0px 10px'
            }}>
                <div>
                    <SelectButton id={0} title='전체' active={category}
                                  onClick={() => handleCategoryChange(0)}/>
                    <SelectButton id={1} title='학교 홈페이지' active={category}
                                  onClick={() => handleCategoryChange(1)}/>
                    <SelectButton id={2} title='학과사무실' active={category}
                                  onClick={() => handleCategoryChange(2)}/>
                    <SelectButton id={3} title='학생회' active={category}
                                  onClick={() => handleCategoryChange(3)}/>
                </div>
                <div className="mr-2 ml-2 align-self-center">
                    <img src={searchImage} className="search-icon" onClick={searchContents}/>
                    <input type="text" value={searchData.keyword} onChange={handleSearchTextChange}
                           onKeyPress={searchEnterPress} className="search-box" placeholder='검색'/>
                    <select className="search-type" value={searchData.search_type}
                            onChange={handleSearchTypeChange}>
                        <option value="select_title">제목</option>
                        <option value="select_title_content">제목+내용</option>
                        <option value="select_writer">작성자</option>
                    </select>
                </div>
            </Row>

            <MakeNoticeList
                category={category}
                match={match}
                searchData={searchData}
                setSearchData={setSearchData}
            />

            {decoded && NOTICE_WRITE_ROLE.includes(decoded.role) ?
                <BlueButton type='/notice/newNotice' title="글쓰기"/>
                : null
            }

        </div>
    );
}



