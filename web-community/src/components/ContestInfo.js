import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import contestImage1 from "../image/contest1.svg";
import ContestInfoCard from "./ComponentInfoCard";
import Pagination from "./PaginationCustom";
import Title from "./Title";

function ContestInfo() {
    return (
        <div className='ContestInfo'>
            <Container>
                <Title text='공모전 / 대외활동' type='1'/>
                <Row style={{marginTop: '3rem'}}>
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
                <Pagination active={1}/>
            </Container>

        </div>
    );
}
export default ContestInfo;
