import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import jobLogo from "../image/Kakao.png";
import jobLogo2 from "../image/naver.png";
import searchImage from "../icon/search.svg"
import React from "react";
import Card from "react-bootstrap/Card";
import placeImage from "../icon/place.svg";
import monitorImage from "../icon/monitor.svg";
import Pagination from "./PaginationCustom";
import Title from "./Title";
import JobCard from "./JobCard";

export default function JobInfo() {
    return (
        <div className="Job">
            <Container>
                <Row style={{marginBottom: '1rem'}}>
                    <Col>
                        <Title text='채용 정보' type='1'/>
                    </Col>
                    <Col style={{marginTop: '3rem'}}>
                        <img src={searchImage} style={{float: "right", marginLeft: "10px", height: "25px"}}/>
                        <input type="text" className={"search-box"} placeholder={'검색'}/>
                    </Col>
                </Row>
                <Row style={{marginBottom: '2rem'}}>
                    <Col>
                        <Button className={classNames("select-btn", "on")}>JAVA</Button>
                        <Button className={classNames("select-btn", "off")}>Spring</Button>
                        <Button className={classNames("select-btn", "off")}>Node.js</Button>
                        <Button className={classNames("select-btn", "off")}>Django</Button>
                        <Button className={classNames("select-btn", "off")}>React.js</Button>
                        <Button className={classNames("select-btn", "off")}>Vue.js</Button>
                        <Button className={classNames("select-btn", "off")}>Javascript</Button>
                        <Button className={classNames("select-btn", "off")}>Python</Button>
                        <Button className={classNames("select-btn", "off")}>Kotlin</Button>
                        <Button className={classNames("select-btn", "off")}>C++</Button>
                        <Button className={classNames("select-btn", "off")}>웹 풀스택</Button>
                        <Button className={classNames("select-btn", "off")}>웹 프론트엔드</Button>
                        <Button className={classNames("select-btn", "off")}>웹 백엔드</Button>
                        <Button className={classNames("select-btn", "off")}>안드로이드</Button>
                        <Button className={classNames("select-btn", "off")}>ios</Button>
                        <Button className={classNames("select-btn", "off")}>서버/백엔드</Button>
                    </Col>
                </Row>
                <div>
                    <Row className="mb-3">
                        <Col lg={6} md={12} sm={12}>
                            <JobCard image={jobLogo} title='커머스 서버 개발 전문가'
                                     host='중고나라' place='서울시 강남구' position='신입,인턴' detail={true}/>
                        </Col>
                        <Col lg={6} md={12} sm={12}>
                            <JobCard image={jobLogo2} title='커머스 서버 개발 전문가'
                                     host='중고나라' place='서울시 강남구' position='신입,인턴' detail={true}/>
                        </Col>
                        <Col lg={6} md={12} sm={12}>
                            <JobCard image={jobLogo} title='커머스 서버 개발 전문가'
                                     host='중고나라' place='서울시 강남구' position='신입,인턴' detail={true}/>
                        </Col>
                        <Col lg={6} md={12} sm={12}>
                            <JobCard image={jobLogo2} title='커머스 서버 개발 전문가'
                                     host='중고나라' place='서울시 강남구' position='신입,인턴' detail={true}/>
                        </Col>
                    </Row>
                </div>

                <Pagination active={1}/>
            </Container>
        </div>

    )
}
