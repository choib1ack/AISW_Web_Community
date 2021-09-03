import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link, useHistory} from "react-router-dom";
import fileImage from "../../icon/file.svg";
import Loading from "../Loading";
import Pagination from "../PaginationCustom";
import './Board.css';
import '../Notice/Notice.css';

export default function MakeBoardList(props) {

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [boardData, setBoardData] = useState(
        {
            fix_notice: null,
            fix_urgent: null,
            normal: {
                page_info: {
                    "current_page": 0,
                    "total_pages": 1
                },
                data: null
            }
        }
    );

    const [curPage, setCurPage] = useState(0);

    const setPagination = (now_page) => {
        setCurPage(now_page);
    }

    let search_data = props.searchData;

    const url = (category) => {
        let url = "/board"
        switch (category) {
            case 0:
                url += "/main";
                break;
            case 1:
                url += "/free";
                break;
            case 2:
                url += "/qna";
                break;
            case 3:
                url += "/job";
                break;
        }
        if (search_data.search > 0) {
            if (category == 0) {
                url = url.substring(0, url.length - 5);
            }
            switch (search_data.search_type) {
                case "select_title":
                    url += "/search/title?title=" + search_data.keyword;
                    break;
                case "select_title_content":
                    url += "/search/title&content?title=" + search_data.keyword + "&content=" + search_data.keyword;
                    break;
                case "select_writer":
                    url += "/search/writer?writer=" + search_data.keyword;
                    break;
            }
        }
        console.log(props.selected_subject_list)
        if (props.selected_subject_list.length !== 0) {
            url += "/subject";
        }
        url += search_data.search > 0 ? "" : "?page=" + curPage;
        if (props.selected_subject_list.length !== 0) {
            url += "&subject=" + props.selected_subject_list.join(",");
        }
        console.log(url);
        return url;
    }

    const categoryName = (category) => {
        switch (category) {
            case 0:
                return 0;
            case 1:
                return "free";
            case 2:
                return "qna";
            case 3:
                return "job";
        }
    }

    const status = (status) => {
        switch (status) {
            case "URGENT":
                return '긴급';
            case "NOTICE":
                return "공지";
            // case "GENERAL":
            //     return "일반";
        }
    }


    const indexing = (index) => {
        let current_max = boardData.normal.page_info.total_elements - (curPage * 10); // 현재 페이지에서 max값
        return current_max - index.toString();
    }

    const attachment = (file) => {
        let attached = false;
        if (file != null) {
            attached = true;
        }

        let style = {
            marginLeft: '5px',
            display: attached ? "" : "none"
        }
        return style;
    }

    // <tr> 전체에 링크 연결
    let history = useHistory();
    const ToLink = (url) => {
        history.push(`/board/${props.category}`);
        history.push(url);
    }

    useEffect(() => {
        const fetchNoticeData = async () => {
            try {

                setError(null);
                setLoading(true);

                await axios.get(url(props.category))
                    .then((res)=>{
                        if (boardData.normal.page_info.current_page === 0) { // 페이지가 1일때만 top꺼 가져오고, 2번째부터는 그대로 씀
                            setBoardData({
                                ...boardData,
                                fix_notice: res.data.data.board_api_notice_response_list,
                                fix_urgent: res.data.data.board_api_urgent_response_list,
                                normal: {
                                    page_info: res.data.pagination,
                                    data: res.data.data.board_api_response_list
                                }
                            })
                        } else {
                            setBoardData({
                                ...boardData,
                                normal: {
                                    page_info: res.data.pagination,
                                    data: res.data.data.board_api_response_list
                                }
                            })
                        }
                        setLoading(false);
                    })

            } catch (e) {
                setError(e);
            }
        };

        fetchNoticeData();
    }, [props.category, props.searchData.search, props.selected_subject_list, curPage]);


    if (error) return (
        <tr>
            <td colSpan={5}>에러가 발생했습니다{error.toString()}</td>
        </tr>
    );
    if (loading) return <Loading/>;
    if (!boardData.normal.data || boardData.normal.data.length === 0)
        return (
            <table className="table-style">
                <thead>
                <tr>
                    <th>no</th>
                    <th className="table-title">제목</th>
                    <th>작성자</th>
                    <th>등록일</th>
                    <th>조회</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td colSpan={5}>데이터가 없습니다.</td>
                </tr>
                </tbody>
            </table>
        );
    return (
        <>
            <table className="table-style">
                <thead>
                <tr>
                    <th>no</th>
                    <th className="table-title">제목</th>
                    <th>작성자</th>
                    <th>등록일</th>
                    <th>조회</th>
                </tr>
                </thead>
                <tbody>
                {boardData.fix_urgent !== null && props.searchData.search == 0 ? boardData.fix_urgent.map(data => (
                    <tr key={data.id}
                        onClick={() => ToLink(`/board/${categoryName(props.category) === 0 ?
                            data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                        <td>{status(data.status)}</td>
                        <td>
                            {data.title}
                            <img src={fileImage} style={attachment(data.attachment_file)}/>
                        </td>
                        <td>{data.writer}</td>
                        <td>{data.created_at.substring(0, 10)}</td>
                        <td>{data.views}</td>
                    </tr>
                )) : null}
                {boardData.fix_notice !== null && props.searchData.search == 0 ? boardData.fix_notice.map(data => (
                    <tr key={data.id}
                        onClick={() => ToLink(`/board/${categoryName(props.category) === 0 ?
                            data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                        <td>{status(data.status)}</td>
                        <td>
                            {data.title}
                            <img src={fileImage} style={attachment(data.attachment_file)}/>
                        </td>
                        <td>{data.writer}</td>
                        <td>{data.created_at.substring(0, 10)}</td>
                        <td>{data.views}</td>
                    </tr>
                )) : null}
                {boardData.normal.data.map((data, index) =>
                    (
                        <tr key={data.id}
                            onClick={() => ToLink(`/board/${categoryName(props.category) === 0 ?
                                data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                            <td>{indexing(index)}</td>
                            <td>
                                {data.title}
                                <img src={fileImage} style={attachment(data.attachment_file)}/>
                            </td>
                            <td>{data.writer}</td>
                            <td>{data.created_at.substring(0, 10)}</td>
                            <td>{data.views}</td>
                        </tr>

                    ))}
                </tbody>
            </table>
            <Pagination
                current_page={curPage}
                total_pages={boardData.normal.page_info.total_pages}
                setPagination={setPagination}
            />
        </>
    );
}
