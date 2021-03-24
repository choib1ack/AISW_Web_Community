import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link, useHistory} from "react-router-dom";
import fileImage from "../../icon/file.svg";
import Loading from "../Loading";

export default function MakeBoardList(props) {
    const [boardData, setBoardData] = useState(null);
    const [boardFixData, setBoardFixData] = useState(null);
    const [urgentFixData, setUrgentFixData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [pageInfo, setPageInfo] = useState(null);

    let is_search = props.is_search;
    let search_type = props.search_type;
    let search_text = props.search_text;
    let selected_subject_list = props.selected_subject_list;

    const url = (category, page) => {
        let url = "/board"
        switch (category) {
            case 0:
                // 요거 전체로 바꿔야함
                url += "/main";
                break;
            case 1:
                url += "/free";
                break;
            case 2:
                url += "/qna";
                break;
        }
        if (is_search) {
            switch (search_type) {
                case "select_title":
                    url += "/search/title?title=" + search_text;
                    break;
                case "select_title_content":
                    url += "/search/title&content?title=" + search_text + "&content=" + search_text;
                    break;
                case "select_writer":
                    url += "/search/writer?writer=" + search_text;
                    break;
            }
        }
        if(selected_subject_list.length!=0){
            url += "/subject";
        }
        url += "?page=" + (props.current_page);
        if(selected_subject_list.length!=0){
            console.log("서브젝트");
            url += "&subject=" + selected_subject_list.join(",");
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
        }
    }

    const status = (status) =>{
        switch (status) {
            case "URGENT":
                return '긴급';
            case "NOTICE":
                return "공지";
            // case "GENERAL":
            //     return "일반";
        }
    }

    const indexing = (index) =>{
        let current_max = pageInfo.total_elements-(pageInfo.current_page*10);
        return current_max-index.toString();
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
    // console.log("하하하")
    // console.log(selected_subject_list)
    useEffect(() => {
        const fetchNoticeData = async () => {
            try {
                setError(null);
                setBoardData(null);
                setLoading(true);
                const response = await axios.get(url(props.category));
                setBoardData(response.data.data.board_api_response_list);
                if(props.current_page==0){ // 페이지가 1일때만 top꺼 가져오고, 2번째부터는 그대로 씀
                    setBoardFixData(response.data.data.board_api_notice_response_list);
                    setUrgentFixData(response.data.data.board_api_urgent_response_list);
                }
                setPageInfo(response.data.pagination);
                props.setTotalPage(response.data.pagination.total_pages);
                props.setNowSearchText("");
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, [props.category, props.search_text, props.current_page, props.selected_subject_list]);

    if (loading) return <Loading/>;
    if (error) return <tr>
        <td colSpan={5}>에러가 발생했습니다{error.toString()}</td>
    </tr>;
    if (!boardData) return null;
    if (Object.keys(boardData).length == 0) return <tr>
        <td colSpan={5}>데이터가 없습니다.</td>
    </tr>;
    return (
        <>
            {urgentFixData.map(data => (
                <tr key={data.id}
                    onClick={() => ToLink(`${props.match.url}/${categoryName(props.category) == 0 ?
                        data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                    <td>{status(data.status)}</td>
                    <td>
                        {data.title}
                        <img src={fileImage} style={attachment(data.attachment_file)}/>
                    </td>
                    <td>{data.created_by}</td>
                    <td>{data.created_at.substring(0, 10)}</td>
                    <td>{data.views}</td>
                </tr>
            ))}
            {boardFixData.map(data => (
                <tr key={data.id}
                    onClick={() => ToLink(`${props.match.url}/${categoryName(props.category) == 0 ?
                        data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                    <td>{status(data.status)}</td>
                    <td>
                        {data.title}
                        <img src={fileImage} style={attachment(data.attachment_file)}/>
                    </td>
                    <td>{data.created_by}</td>
                    <td>{data.created_at.substring(0, 10)}</td>
                    <td>{data.views}</td>
                </tr>
            ))}
            {boardData.map((data, index) => (
                <tr key={data.notice_id}
                    onClick={()=>ToLink(`${props.match.url}/${categoryName(props.category) == 0 ?
                        data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                    <td>{indexing(index)}</td>
                    <td>
                        {data.title}
                        <img src={fileImage} style={attachment(data.attachment_file)}/>
                    </td>
                    <td>{data.created_by}</td>
                    <td>{data.created_at.substring(0,10)}</td>
                    <td>{data.views}</td>
                </tr>

            ))}
        </>
    );
}
