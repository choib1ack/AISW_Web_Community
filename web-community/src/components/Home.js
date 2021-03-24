import React, {useEffect, useState} from "react";
import ApiTest from "./ApiTest";
import MyPage from "./MyPage";
import searchImage from "../icon/search_black.png";
import exampleBanner from "../image/banner_example1.svg"
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import JobCard from "./JobCard";
import jobLogo from "../image/Kakao.png";
import jobLogo2 from "../image/naver.png";
import ContestInfoCard from "./ComponentInfoCard";
import contestImage1 from "../image/contest1.svg";
import "./Home.css";
import {Link, useHistory} from "react-router-dom";
import axios from "axios";
import fileImage from "../icon/file.svg";
import Loading from "./Loading";

export default function Home() {
    let history = useHistory();
    const ToLink = (url) => {
        history.push(url);
    }

    return (
        <div className="Home" >
            {/*<h1>Home</h1>*/}
            {/*<MyPage/>*/}
            <div className="Banner">
                <img src={exampleBanner} style={{width: "100%"}}/>
            </div>
            <div style={{width: "70%", margin: "auto"}}>
                <Row style={{margin: "30px 0px", textAlign: "center"}}>
                    <Col lg={4} md={4} sm={4}>
                        <Link to="/deptInfo">
                            <div className={"pingpong"} style={{color: "#636363"}}>
                                학과 커리큘럼 확인하기
                            </div>
                        </Link>
                    </Col>
                    <Col lg={4} md={4} sm={4}>
                        <Link to="/">
                            <div className={"pingpong"} style={{color: "#636363"}}>
                                학번별 학사요람 확인하기
                            </div>
                        </Link>
                    </Col>
                    <Col lg={4} md={4} sm={4}>
                        <Link to="/">
                            <div className={"pingpong"} style={{color: "#636363"}}>
                                졸업 요건 확인하기
                            </div>
                        </Link>
                    </Col>
                </Row>
                <Row style={{margin: "20px 0px"}}>
                    <Col lg={6} md={6} sm={6} style={{height: '100%'}}>
                        <div style={{border: "1px solid #E3E3E3", padding: "20px", textAlign: "left", height: "100%"}}>
                            <div style={{marginBottom: "20px"}}>
                                공지사항
                                <Link to="/notice">
                                    <span style={{float: "right", cursor: "pointer", color: "#636363"}}>+더보기</span>
                                </Link>
                            </div>
                            <MakeHomeNoticeList/>
                        </div>
                    </Col>
                    <Col lg={6} md={6} sm={6}>
                        <div style={{border: "1px solid #E3E3E3", padding: "20px", textAlign: "left", height: "100%"}}>
                            <div style={{marginBottom: "20px"}}>
                                게시판
                                <Link to="/board">
                                    <span style={{float: "right", cursor: "pointer", color: "#636363"}}>+더보기</span>
                                </Link>
                            </div>
                            <MakeHomeBoardList/>
                        </div>
                    </Col>
                </Row>
                <Row style={{margin: "20px 0px"}}>
                    <Col lg={12} md={12} sm={12}>
                        <div style={{border: "1px solid #E3E3E3", padding: "20px", textAlign: "left"}}>
                            <div style={{marginBottom: "20px"}}>
                                채용정보
                                <Link to="/jobInfo">
                                    <span style={{float: "right", cursor: "pointer", color: "#636363"}}>+더보기</span>
                                </Link>
                            </div>
                            <div>
                                <Row>
                                    <Col lg={6} md={12} sm={12}>
                                        <JobCard image={jobLogo} title='커머스 서버 개발 전문가'
                                                 host='중고나라' place='서울시 강남구' position='신입,인턴' detail={false}/>
                                    </Col>
                                    <Col lg={6} md={12} sm={12}>
                                        <JobCard image={jobLogo2} title='커머스 서버 개발 전문가'
                                                 host='중고나라' place='서울시 강남구' position='신입,인턴' detail={false}/>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col lg={6} md={12} sm={12}>
                                        <JobCard image={jobLogo} title='커머스 서버 개발 전문가'
                                                 host='중고나라' place='서울시 강남구' position='신입,인턴'/>
                                    </Col>
                                    <Col lg={6} md={12} sm={12}>
                                        <JobCard image={jobLogo2} title='커머스 서버 개발 전문가'
                                                 host='중고나라' place='서울시 강남구' position='신입,인턴'/>
                                    </Col>
                                </Row>
                            </div>
                        </div>

                    </Col>
                </Row>
                <Row style={{margin: "20px 0px"}}>
                    <Col lg={12} md={12} sm={12}>
                        <div style={{border: "1px solid #E3E3E3", padding: "20px", textAlign: "left"}}>
                            <div style={{marginBottom: "20px"}}>
                                공모전/대외활동
                                <Link to="/contestInfo">
                                    <span style={{float: "right", cursor: "pointer", color: "#636363"}}>+더보기</span>
                                </Link>
                            </div>
                            <Row>
                                <Col lg={3} md={4} sm={6}>
                                    <ContestInfoCard image={contestImage1}
                                                     title='인공지능 학습용 데이터 활용 아이디어 공모전'
                                                     host='과학기술정보통신부' date='2020-12-14(월) ~ 2021-01-07(목)'/>
                                </Col>
                                <Col lg={3} md={4} sm={6}>
                                    <ContestInfoCard image={contestImage1}
                                                     title='인공지능 학습용 데이터 활용 아이디어 공모전'
                                                     host='과학기술정보통신부' date='2020-12-14(월) ~ 2021-01-07(목)'/>
                                </Col>
                                <Col lg={3} md={4} sm={6}>
                                    <ContestInfoCard image={contestImage1}
                                                     title='인공지능 학습용 데이터 활용 아이디어 공모전'
                                                     host='과학기술정보통신부' date='2020-12-14(월) ~ 2021-01-07(목)'/>
                                </Col>
                                <Col lg={3} md={4} sm={6}>
                                    <ContestInfoCard image={contestImage1}
                                                     title='인공지능 학습용 데이터 활용 아이디어 공모전'
                                                     host='과학기술정보통신부' date='2020-12-14(월) ~ 2021-01-07(목)'/>
                                </Col>
                            </Row>
                        </div>
                    </Col>
                </Row>
                <div style={{marginBottom: "100px"}}></div>
            </div>

        </div>
    )
}

function MakeHomeNoticeList() {
    const [noticeData, setNoticeData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchNoticeData = async () => {
            try {
                setError(null);
                setNoticeData(null);
                setLoading(true);
                const response = await axios.get("/notice/main");
                // console.log(response);
                setNoticeData(response.data.data.notice_api_response_list); // 데이터는 response.data 안에 있음
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, []);

    let history = useHistory();
    const ToLink = (url) => {
        history.push(url);
    }

    if (loading) return <Loading/>;
    if (error) return <tr>
        <td colSpan={5}>에러가 발생했습니다{error.toString()}</td>
    </tr>;
    if (!noticeData) return null;
    if (Object.keys(noticeData).length == 0) return <tr>
        <td colSpan={5}>데이터가 없습니다.</td>
    </tr>;

    return (
        <>
            {noticeData.map(data => (
                <p onClick={() => ToLink(`/notice/${data.category.toLowerCase()}/${data.id}`)} key={data.notice_id}
                   className={"clickable"}>
                    {data.title} <span style={{float: "right"}}>{data.created_at.substring(0, 10)}</span>
                </p>

            ))}
        </>

    );

}

function MakeHomeBoardList() {
    const [boardData, setBoardData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchNoticeData = async () => {
            try {
                setError(null);
                setBoardData(null);
                setLoading(true);
                const response = await axios.get("/board/main");
                setBoardData(response.data.data.board_api_response_list);
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchNoticeData();
    }, []);

    let history = useHistory();
    const ToLink = (url) => {
        history.push(url);
    }

    if (loading) return <tr>
        <td colSpan={5}>로딩중..</td>
    </tr>;
    if (error) return <tr>
        <td colSpan={5}>에러가 발생했습니다{error.toString()}</td>
    </tr>;
    if (!boardData) return null;
    if (Object.keys(boardData).length == 0) return <tr>
        <td colSpan={5}>데이터가 없습니다.</td>
    </tr>;

    return (
        <>
            {boardData.map(data => (
                <p onClick={() => ToLink(`/board/${data.category.toLowerCase()}/${data.id}`)} key={data.notice_id}
                   className={"clickable"}>
                    {data.title} <span style={{float: "right"}}>{data.created_at.substring(0, 10)}</span>
                </p>

            ))}
        </>

    );

}
