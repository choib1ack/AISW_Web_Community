import React, {useEffect, useState} from "react";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import "./Home.css";
import {Link, useHistory} from "react-router-dom";
import axios from "axios";
import Loading from "./Loading";
import HomeSiteImageSlide from "./HomeSiteImageSlide";
import {CarouselList} from "./AdminPage/CarouselList";

export default function Home() {

    let history = useHistory();
    const ToLink = (url) => {
        history.push(url);
    }

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [homeData, setHomeData] = useState(null);

    useEffect(() => {
        const fetchHomeData = async () => {
            try {
                if (homeData != null) {
                    return;
                }

                setLoading(true);
                setError(null);

                await axios.get("/home")
                    .then(res => {
                            setHomeData(res.data.data);
                        }
                    );

            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchHomeData();
    }, []);


    if (loading) return <Loading/>;
    if (error) return <div>에러가 발생했습니다{error.toString()}</div>;
    if (!homeData) return <div>데이터가 없습니다.</div>;

    return (
        <div className="Home">
            <CarouselList
                banners={homeData.banner_list}
                page="Home"
            />

            {/* 통합검색 - 일단 보류 */}
            {/*<div className="IntegratedSearch" style={{width: "70%", marginLeft: "auto", marginRight: "auto", marginTop: "30px"}}>*/}
            {/*    <IntegratedSearch/>*/}
            {/*</div>*/}
            <div style={{margin: "auto"}} className="content">
                <Row style={{margin: "30px 0px", textAlign: "center"}}>
                    <Col lg={4} md={4} sm={4}>
                        <Link to="/deptInfo">
                            <div className="pingpong" style={{color: "#636363"}}>
                                학과 커리큘럼 확인하기
                            </div>
                        </Link>
                    </Col>
                    <Col lg={4} md={4} sm={4}>
                        <Link to="/Booklet">
                            <div className="pingpong" style={{color: "#636363"}}>
                                학번별 학사요람 확인하기
                            </div>
                        </Link>
                    </Col>
                    <Col lg={4} md={4} sm={4}>
                        <Link to="/GraduateCondition">
                            <div className="pingpong" style={{color: "#636363"}}>
                                졸업 요건 확인하기
                            </div>
                        </Link>
                    </Col>
                </Row>
                <Row style={{margin: "20px 0px"}}>
                    <Col lg={6} md={6} sm={6}>
                        <div style={{border: "1px solid #E3E3E3", padding: "20px", textAlign: "left", height: "100%"}}>
                            <div style={{marginBottom: "20px"}}>
                                공지사항
                                <Link to="/notice">
                                    <span style={{float: "right", cursor: "pointer", color: "#636363"}}>+더보기</span>
                                </Link>
                            </div>
                            <MakeHomeNoticeList
                                noticeData={homeData.notice_list}
                            />
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
                            <MakeHomeBoardList
                                boardData={homeData.board_list}
                            />
                        </div>
                    </Col>
                </Row>
                <Row style={{marginTop: "100px"}}>
                    <Col style={{padding: "0px 30px"}}>
                        {/*<div style={{border: "1px solid #E3E3E3", padding: "20px", textAlign: "left", height: "100%"}}>*/}
                        {/*    <div style={{marginBottom: "20px", textAlign: "left"}}>*/}
                        {/*        볼만한 사이트*/}
                        {/*    </div>*/}
                        <HomeSiteImageSlide
                            siteInfo={homeData.site_list}
                        />
                        {/*</div>*/}
                    </Col>
                </Row>
                <div style={{marginBottom: "100px"}}></div>
            </div>

        </div>
    )
}

function MakeHomeNoticeList({noticeData}) {

    let history = useHistory();
    const ToLink = (url) => {
        history.push(url);
    }

    if (!noticeData) return null;
    if (Object.keys(noticeData).length === 0) return <p>데이터가 없습니다.</p>;

    return (
        <>
            {noticeData.map((data, index) => (
                <p onClick={() => ToLink(`/notice/${data.category.toLowerCase()}/${data.id}`)} key={index}
                   className={"clickable"}>
                    {data.title} <span style={{float: "right"}}>{data.created_at.substring(0, 10)}</span>
                </p>
            ))}
        </>
    );
}

function MakeHomeBoardList({boardData}) {

    let history = useHistory();
    const ToLink = (url) => {
        history.push(url);
    }

    if (!boardData) return null;
    if (Object.keys(boardData).length === 0) return <p>데이터가 없습니다.</p>;

    return (
        <>
            {boardData.map((data, index) => (
                <p onClick={() => ToLink(`/board/${data.category.toLowerCase()}/${data.id}`)} key={index}
                   className={"clickable"}>
                    {data.title} <span style={{float: "right"}}>{data.created_at.substring(0, 10)}</span>
                </p>
            ))}
        </>
    );
}
