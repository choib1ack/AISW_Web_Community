import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link, useHistory} from "react-router-dom";
import fileImage from "../../icon/file.svg";

export default function MakeBoardList(props) {
    const [boardData, setBoardData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

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
            case "TOP":
                return "상단";
            case "GENERAL":
                return "일반";
        }
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
                setBoardData(null);
                setLoading(true);
                console.log("url : "+url(props.category));
                const response = await axios.get(url(props.category));
                console.log(response.data);
                setBoardData(response.data.data);
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, [props.category]);

    if (loading) return <tr><td colSpan={5}>로딩중..</td></tr>;
    if (error) return <tr><td colSpan={5}>에러가 발생했습니다{error.toString()}</td></tr>;
    if (!boardData) return null;
    if (Object.keys(boardData).length==0) return <tr><td colSpan={5}>데이터가 없습니다.</td></tr>;
    return (
        <>
            {boardData.map(data => (
                <tr key={data.id}
                    onClick={()=>ToLink(`${props.match.url}/${categoryName(props.category) == 0 ?
                        data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                    <td>{status(data.status)}</td>
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