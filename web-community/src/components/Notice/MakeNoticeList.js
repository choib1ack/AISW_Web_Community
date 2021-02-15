import React, {useEffect, useState} from "react";
import axios from "axios";
import { useHistory } from "react-router-dom";
import fileImage from "../../icon/file.svg";

export default function MakeNoticeList(props) {
    const [noticeData, setNoticeData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const url = (category, page) => {
        let url = "/notice"
        switch (category) {
            case 0:
                // 요거 전체로 바꿔야함
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
        return url;
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
                // 요청이 시작 할 때에는 error 와 users 를 초기화하고
                setError(null);
                setNoticeData(null);
                // loading 상태를 true 로 바꿉니다.
                setLoading(true);
                console.log("url : "+url(props.category));
                const response = await axios.get(url(props.category));
                // const response = await axios.get("/notice/university");
                console.log(response.data);
                setNoticeData(response.data.data); // 데이터는 response.data 안에 들어있습니다.
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, [props.category]);

    if (loading) return <tr><td colSpan={5}>로딩중..</td></tr>;
    if (error) return <tr><td colSpan={5}>에러가 발생했습니다{error.toString()}</td></tr>;
    if (!noticeData) return null;
    if (Object.keys(noticeData).length==0) return <tr><td colSpan={5}>데이터가 없습니다.</td></tr>;
    return (
        <>
            {noticeData.map(data => (

                <tr key={data.notice_id}
                    onClick={()=>ToLink(`${props.match.url}/${categoryName(props.category) == 0 ? 
                        data.category.toLowerCase() : categoryName(props.category)}/${data.id}`)}>
                    <td>{status(data.status)}</td>
                    <td>
                            {data.title}
                            {/*<img src={photoImage} style={attachment(data.attachment_file)}/>*/}
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