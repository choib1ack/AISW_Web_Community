import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import contestImage1 from "../image/contest1.svg";
import Pagination from "react-bootstrap/Pagination";

function ContestInfo() {
    let active = 1;
    let items = [];
    for (let number = 1; number <= 5; number++) {
        items.push(
            <Pagination.Item key={number} active={number === active}>
                {number}
            </Pagination.Item>,
        );
    }
    return (
        <div className='ContestInfo'>
            <Container>
                <p className={"title"}>공모전/대외활동</p>
                <Row style={{marginBottom: '1rem', marginTop: '3rem'}}>
                    <Col lg={3} md={4} sm={6}>
                        <ContestInfoCard/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <ContestInfoCard/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <ContestInfoCard/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <ContestInfoCard/>
                    </Col>
                </Row>
                <Row style={{marginBottom: '3rem', marginTop: '2rem'}}>
                    <Col lg={3} md={4} sm={6}>
                        <ContestInfoCard/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <ContestInfoCard/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <ContestInfoCard/>
                    </Col>
                    <Col lg={3} md={4} sm={6}>
                        <ContestInfoCard/>
                    </Col>
                </Row>

            </Container>
            <Pagination size="sm" className="align-self-center justify-content-center" style={{marginBottom: '3rem'}}>{items}</Pagination>
        </div>
    );
}
export default ContestInfo;

// 어떻게 하는거지 ㅎ
// function ContestInfoCard(image, title, host, date) {
//     return (
//         <Card style={{width: '100%'}} className="text-left">
//             <img src={image} style={{width: '100%', height:'50%'}}/>
//             <Card.Body>
//                 <Card.Title style={{fontSize: '15px'}}>{title}</Card.Title>
//                 <Card.Subtitle style={{fontSize: '12px', color:'#B8B8B8'}}>{host}</Card.Subtitle>
//
//                 <Card.Text style={{marginTop: '0.5rem', marginBottom:'0'}}>
//                     <span style={{fontSize: '14px', fontWeight:'bold', color:'#6CBACB'}}> D-1<br/></span>
//                     <span style={{fontSize: '13px'}}>{date}</span>
//                 </Card.Text>
//             </Card.Body>
//         </Card>
//     )
// }
function ContestInfoCard(image, title, host, date) {
    return (
        <Card style={{width: '100%'}} className="text-left">
            <img src={contestImage1} style={{width: '100%', height:'50%'}}/>
            <Card.Body>
                <Card.Title style={{fontSize: '15px'}}>인공지능 학습용 데이터 활용 아이디어 공모전</Card.Title>
                <Card.Subtitle style={{fontSize: '12px', color:'#B8B8B8'}}>과학기술정보통신부</Card.Subtitle>

                <Card.Text style={{marginTop: '0.5rem', marginBottom:'0'}}>
                    <span style={{fontSize: '14px', fontWeight:'bold', color:'#6CBACB'}}> D-1<br/></span>
                    <span style={{fontSize: '13px'}}>2020-12-14(월) ~ 2021-01-07(목)</span>
                </Card.Text>
            </Card.Body>
        </Card>
    )
}