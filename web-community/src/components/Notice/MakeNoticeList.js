import React, {useEffect, useState} from "react";
import axios from "axios";
import { useHistory } from "react-router-dom";
import fileImage from "../../icon/file.svg";
import Loading from "../Loading";

export default function MakeNoticeList(props) {
    const [noticeData, setNoticeData] = useState(null);
    const [noticeFixData, setNoticeFixData] = useState(null);
    const [urgentFixData, setUrgentFixData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [pageInfo, setPageInfo] = useState(null);

    let is_search = props.is_search;
    let search_type = props.search_type;
    let search_text = props.search_text;

    const url = (category, page) => {
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
        if(is_search){
            switch (search_type) {
                case "select_title":
                    url += "/search/title?title="+search_text;
                    break;
                case "select_title_content":
                    url += "/search/title&content?title="+search_text+"&content="+search_text;
                    break;
                case "select_writer":
                    url += "/search/writer?writer="+search_text;
                    break;
            }
        }
        url+="?page="+(props.current_page);
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
        console.log("index="+index+", current_max="+current_max);
        return current_max-index.toString();
    }

    const attachment = (file) =>{
        let attached = false;
        if(file != null){
            attached = true;
        }

        let style = {
            marginLeft: '5px',
            display: attached? "" : "none"
        }
        return style;
    }

    // <tr> 전체에 링크 연결
    let history = useHistory();
    const ToLink = (url) =>{
        history.push(url);
    }

    useEffect(() => {
        const fetchNoticeData = async () => {
            try {
                setError(null);
                setNoticeData(null);
                setLoading(true);
                const response = await axios.get(url(props.category));
                if(props.current_page==0){ // 페이지가 1일때만 top꺼 가져오고, 2번째부터는 그대로 씀
                    setNoticeFixData(response.data.data.notice_api_notice_response_list)
                    setUrgentFixData(response.data.data.notice_api_urgent_response_list)
                }
                setNoticeData(response.data.data.notice_api_response_list); // 데이터는 response.data 안에 있음
                setPageInfo(response.data.pagination);
                props.setTotalPage(response.data.pagination.total_pages);
                props.setNowSearchText("");
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, [props.category, props.search_text, props.current_page]);

    if (loading) return <Loading/>;
    if (error) return <tr><td colSpan={5}>에러가 발생했습니다{error.toString()}</td></tr>;
    if (!noticeData) return null;
    if (Object.keys(noticeData).length==0) return <tr><td colSpan={5}>데이터가 없습니다.</td></tr>;
    return (
        <>
            {urgentFixData.map(data => (
                <tr key={data.notice_id}
                    onClick={()=>ToLink(`${props.match.url}/${categoryName(props.category) == 0 ?
                        data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                    <td>{status(data.status)}</td>
                    <td>
                        {data.title}
                        <img src={fileImage} style={attachment(data.attachment_file)}/>
                    </td>
                    <td>{data.writer}</td>
                    <td>{data.created_at.substring(0,10)}</td>
                    <td>{data.views}</td>
                </tr>

            ))}
            {noticeFixData.map(data => (
                <tr key={data.notice_id}
                    onClick={()=>ToLink(`${props.match.url}/${categoryName(props.category) == 0 ?
                        data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                    <td>{status(data.status)}</td>
                    <td>
                        {data.title}
                        <img src={fileImage} style={attachment(data.attachment_file)}/>
                    </td>
                    <td>{data.writer}</td>
                    <td>{data.created_at.substring(0,10)}</td>
                    <td>{data.views}</td>
                </tr>

            ))}

            {noticeData.map((data, index) => (
                <tr key={data.notice_id}
                    onClick={()=>ToLink(`${props.match.url}/${categoryName(props.category) == 0 ? 
                        data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                    <td>{indexing(index)}</td>
                    <td>
                            {data.title}
                            <img src={fileImage} style={attachment(data.attachment_file)}/>
                    </td>
                    <td>{data.writer}</td>
                    <td>{data.created_at.substring(0,10)}</td>
                    <td>{data.views}</td>
                </tr>

            ))}
        </>
    );
}
