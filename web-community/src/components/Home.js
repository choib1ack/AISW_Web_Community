import React from "react";
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

export default function Home() {
    let history = useHistory();
    const ToLink = (url) =>{
        history.push(url);
    }

    return(
        <div className="Home">
            {/*<h1>Home</h1>*/}
            {/*<MyPage/>*/}
            <div className="Banner">
                <img src={exampleBanner} style={{width:"100%"}}/>
            </div>
        <div style={{width:"70%", margin:"auto"}}>
            <Row style={{margin:"30px 0px", textAlign:"center"}}>
                <Col lg={4} md={4} sm={4}>
                    <Link to="/deptInfo">
                        <div className={"pingpong"} style={{color:"#636363"}}>
                            학과 커리큘럼 확인하기
                        </div>
                    </Link>
                </Col>
                <Col lg={4} md={4} sm={4}>
                    <Link to="/">
                        <div className={"pingpong"} style={{color:"#636363"}}>
                            학번별 학사요람 확인하기
                        </div>
                    </Link>
                </Col>
                <Col lg={4} md={4} sm={4}>
                    <Link to="/">
                        <div className={"pingpong"} style={{color:"#636363"}}>
                            졸업 요건 확인하기
                        </div>
                    </Link>
                </Col>
            </Row>
            <Row style={{margin:"20px 0px"}}>
                <Col lg={6} md={6} sm={6}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px", backgroundColor:"#EFF7F9", textAlign:"left"}}>
                        <div style={{marginBottom:"20px"}}>
                            공지사항
                            <Link to="/notice">
                                <span style={{float:"right", cursor:"pointer", color:"#636363"}}>+더보기</span>
                            </Link>
                        </div>
                        <div>
                            <p onClick={()=>ToLink('')} className={"clickable"}>
                                2021-1학기 수강신청 안내 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p onClick={()=>ToLink('')} className={"clickable"}>
                                2021-1학기 수강신청 안내 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p onClick={()=>ToLink('')} className={"clickable"}>
                                2021-1학기 수강신청 안내 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p onClick={()=>ToLink('')} className={"clickable"}>
                                2021-1학기 수강신청 안내 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p onClick={()=>ToLink('')} className={"clickable"}>
                                2021-1학기 수강신청 안내 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p onClick={()=>ToLink('')} className={"clickable"}>
                                2021-1학기 수강신청 안내 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                        </div>
                    </div>
                </Col>
                <Col lg={6} md={6} sm={6}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px", backgroundColor:"#EFF7F9", textAlign:"left"}}>
                        <div style={{marginBottom:"20px"}}>
                            게시판
                            <Link to="/board">
                                <span style={{float:"right", cursor:"pointer", color:"#636363"}}>+더보기</span>
                            </Link>
                        </div>
                        <div>
                            <p>
                                3학년 질문이 있습니다 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p>
                                3학년 질문이 있습니다 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p>
                                3학년 질문이 있습니다 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p>
                                3학년 질문이 있습니다 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p>
                                3학년 질문이 있습니다 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                            <p>
                                3학년 질문이 있습니다 <span style={{float:"right"}}>2021-01-06</span>
                            </p>
                        </div>
                    </div>
                </Col>
            </Row>
            <Row style={{margin:"20px 0px"}}>
                <Col lg={12} md={12} sm={12}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px", textAlign:"left"}}>
                        <div style={{marginBottom:"20px"}}>
                            채용정보
                            <Link to="/jobInfo">
                                <span style={{float:"right", cursor:"pointer", color:"#636363"}}>+더보기</span>
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
            <Row style={{margin:"20px 0px"}}>
                <Col lg={12} md={12} sm={12}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px", textAlign:"left"}}>
                        <div style={{marginBottom:"20px"}}>
                            공모전/대외활동
                            <Link to="/contestInfo">
                                <span style={{float:"right", cursor:"pointer", color:"#636363"}}>+더보기</span>
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
            <div style={{marginBottom:"100px"}}></div>
        </div>

        </div>
    )
}
