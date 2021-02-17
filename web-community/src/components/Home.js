import React from "react";
import ApiTest from "./ApiTest";
import MyPage from "./MyPage";
import searchImage from "../icon/search_black.png";
import exampleBanner from "../image/banner_example1.svg"
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
export default function Home() {
    return(
        <div className="Home">
            {/*<h1>Home</h1>*/}
            {/*<MyPage/>*/}
            <div className="Banner">
                <img src={exampleBanner} style={{width:"100%"}}/>
            </div>
        <div style={{width:"70%", margin:"auto"}}>
            <Row style={{margin:"20px 0px", textAlign:"center"}}>
                <Col lg={4} md={4} sm={4}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px"}}>
                        학과 커리큘럼 확인하기
                    </div>

                </Col>
                <Col lg={4} md={4} sm={4}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px"}}>
                        학번별 학사요람 확인하기
                    </div>

                </Col>
                <Col lg={4} md={4} sm={4}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px"}}>
                        졸업 요건 확인하기
                    </div>

                </Col>
            </Row>
            <Row style={{margin:"20px 0px"}}>
                <Col lg={6} md={6} sm={6}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px", backgroundColor:"#EFF7F9", textAlign:"left", height:"300px"}}>
                        공지사항
                    </div>

                </Col>
                <Col lg={6} md={6} sm={6}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px", backgroundColor:"#EFF7F9", textAlign:"left", height:"300px"}}>
                        게시판
                    </div>
                </Col>
            </Row>
            <Row style={{margin:"20px 0px"}}>
                <Col lg={12} md={12} sm={12}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px", textAlign:"left", height:"300px"}}>
                        채용정보
                    </div>

                </Col>
            </Row>
            <Row style={{margin:"20px 0px"}}>
                <Col lg={12} md={12} sm={12}>
                    <div style={{border:"1px solid #E3E3E3", padding:"20px", textAlign:"left", height:"300px"}}>
                        공모전/대외활동
                    </div>

                </Col>
            </Row>
            <div style={{marginBottom:"100px"}}></div>
        </div>

        </div>
    )
}
