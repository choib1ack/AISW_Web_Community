import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import contestImage1 from "../image/contest1.svg";
import programmersImage from "../siteImages/programmers.png";
import baekjoonImage from "../siteImages/baekjoon.png";
import goormImage from "../siteImages/goorm.png";
import goormLevelImage from "../siteImages/goorm_level.png";
import lifeCodingImage from "../siteImages/life_coding.png";
import swExpertImage from "../siteImages/sw_expert.png";
import inflearnImage from "../siteImages/inflearn.png";
import fastCampusImage from "../siteImages/fast_campus.png";
import saraminImage from "../siteImages/saramin.png";
import jabkoreaImage from "../siteImages/jabkorea.png";
import wantedImage from "../siteImages/wanted.png";
import jasoseolImage from "../siteImages/jasoseol.png";
import wevityImage from "../siteImages/wevity.png";
import onoffmixImage from "../siteImages/onoffmix.png";

import Pagination from "./PaginationCustom";
import Title from "./Title";
import Card from "react-bootstrap/Card";

function ContestInfo() {
    window.scrollTo(0, 0);
    return (
        <div className='ContestInfo'>
            <Container>
                <Title text='유용한 사이트' type='1'/>
                <Title text='코딩테스트 준비' type='2'/>
                <Row>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={baekjoonImage}
                                      title='Baekjoon Online Judge'
                                      url='https://www.acmicpc.net/'
                                      host='알고리즘 문제 풀이 서비스 제공'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={programmersImage}
                                      url='https://programmers.co.kr/'
                                      title='Programmers'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={swExpertImage}
                                      title='SW expert academy'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={goormLevelImage}
                                      title='Goorm'/>
                    </Col>

                </Row>
                <Title text='온라인 강의' type='2'/>
                <Row>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={lifeCodingImage}
                                         title='생활코딩'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={inflearnImage}
                                         title='인프런'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={baekjoonImage}
                                         title='Baekjoon Online Judge'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={fastCampusImage}
                                         title='Fast Campus'/>
                    </Col>
                </Row>
                <Title text='채용 정보' type='2'/>
                <Row>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={jasoseolImage}
                                      title='자소설닷컴'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={jabkoreaImage}
                                      title='잡코리아'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={programmersImage}
                                      url='https://programmers.co.kr/'
                                      title='프로그래머스'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={wantedImage}
                                      title='Wanted'/>
                    </Col>
                </Row>
                <Row>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={saraminImage}
                                      title='사람인'/>
                    </Col>
                </Row>
                <Title text='공모전/대외활동 찾기' type='2'/>
                <Row style={{marginBottom:'3rem'}}>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={wevityImage}
                                      title='위비티'/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <GoodInfoCard image={onoffmixImage}
                                      title='온오프믹스'/>
                    </Col>
                </Row>
                {/*<Pagination active={1}/>*/}
            </Container>

        </div>
    );
}
export default ContestInfo;

function GoodInfoCard(props) {
    return (

        <Card style={{width: '100%', marginBottom: '1rem'}} className="text-center" >
            <a href={props.url}>
                <img src={props.image} style={{width: '100%', height:'50%'}}/>
            </a>
            <Card.Body>
                <Card.Title style={{fontSize: '15px', textAlign:"center"}}>{props.title}</Card.Title>
                {/*<Card.Subtitle style={{fontSize: '12px', color:'#B8B8B8'}}>{props.host}</Card.Subtitle>*/}

                {/*<Card.Text style={{marginTop: '0.5rem', marginBottom:'0'}}>*/}
                {/*    <span style={{fontSize: '14px', fontWeight:'bold', color:'#0472FD'}}> D-1<br/></span>*/}
                {/*    <span style={{fontSize: '13px'}}>{props.date}</span>*/}
                {/*</Card.Text>*/}
            </Card.Body>
        </Card>

    )
}