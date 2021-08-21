import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import fileImage from "../../icon/file.svg";
import Loading from "../Loading";
import axiosApi from "../../axiosApi";
import Pagination from "../PaginationCustom";
import Table from "react-bootstrap/Table";

export default function MakeNoticeList(props) {

    const [noticeData, setNoticeData] = useState(
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

    const setPagination = (now_page) => {
        setNoticeData({
            ...noticeData,
            normal: {
                ...noticeData.normal,
                page_info: {
                    ...noticeData.normal.page_info,
                    "current_page": now_page
                }
            }
        })
    }

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    let search_data = props.searchData;

    const url = (category) => {
        let url = "/notice"
        switch (category) {
            case 0:
                url += "/main";
                break;
            case 1:
                url += "/university";
                break;
            case 2:
                url += "/department";
                break;
            case 3:
                url += "/council";
                break;
        }
        if (search_data.search > 0) {
            if (category === 0) {
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
        url += search_data.search > 0 ? "" : "?page=" + (noticeData.normal.page_info.current_page);
        return url;
    }

    const categoryName = (category) => {
        switch (category) {
            case 0:
                return 0;
            case 1:
                return "university";
            case 2:
                return "department";
            case 3:
                return "council";
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
        let current_max = noticeData.normal.page_info.total_elements - (noticeData.normal.page_info.current_page * 10); // 현재 페이지에서 max값
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
        history.push(url);
    }

    useEffect(() => {
        const fetchNoticeData = async () => {
            try {

                setError(null);
                setLoading(true);

                const response = await axiosApi.get(url(props.category));

                if (noticeData.normal.page_info.current_page === 0) { // 페이지가 1일때만 top꺼 가져오고, 2번째부터는 그대로 씀
                    setNoticeData({
                        ...noticeData,
                        fix_notice: response.data.data.notice_api_notice_response_list,
                        fix_urgent: response.data.data.notice_api_urgent_response_list,
                        normal: {
                            page_info: response.data.pagination,
                            data: response.data.data.notice_api_response_list
                        }
                    })
                } else {
                    setNoticeData({
                        ...noticeData,
                        normal: {
                            page_info: response.data.pagination,
                            data: response.data.data.notice_api_response_list
                        }
                    })
                }

            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, [props.category, props.searchData.search]);

    if (loading) return <Loading/>;
    if (error) return <tr>
        <td colSpan={5}>에러가 발생했습니다{error.toString()}</td>
    </tr>;
    if (!noticeData.normal.data || noticeData.normal.data.length === 0)
        return (
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
                    <td colSpan={5}>데이터가 없습니다.</td>
                </tr>
                </tbody>
            </Table>
        );
    return (
        <>
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
                {noticeData.fix_urgent !== null && props.searchData.search == 0 ? noticeData.fix_urgent.map(data => (
                    <tr key={data.id}
                        onClick={() => ToLink(`${props.match.url}/${categoryName(props.category) === 0 ?
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

                {noticeData.fix_notice !== null && props.searchData.search == 0 ? noticeData.fix_notice.map(data => (
                    <tr key={data.id}
                        onClick={() => ToLink(`${props.match.url}/${categoryName(props.category) === 0 ?
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

                {noticeData.normal.data.map((data, index) => (
                    <tr key={data.id}
                        onClick={() => ToLink(`${props.match.url}/${categoryName(props.category) === 0 ?
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
            </Table>
            <Pagination
                pageInfo={noticeData.normal.page_info}
                setPagination={setPagination}/>
        </>
    );
}
