import React from "react";
import Container from "react-bootstrap/Container";
import fileImage from "../../icon/file.svg";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Title from "../Title";
import { useHistory } from "react-router-dom";

export default function NoticeDetail() {
    let history = useHistory();

    return (
        <div className="NoticeDetail">
            <Container>
                <Title text='공지사항' type='1'/>
                <div className="text-left mt-5 mb-4"
                     style={{borderTop: 'solid 2px #6CBACB', borderBottom: 'solid 2px #6CBACB'}}>
                    <div style={{
                        backgroundColor: "#EFF7F9",
                        paddingTop: '20px',
                        paddingLeft: '20px',
                        paddingBottom: '10px'
                    }}>
                        <p style={{color: "#6CBACB", fontSize: '14px'}} className="mb-1">학과사무실></p>
                        <p style={{fontSize: '18px'}} className="d-inline-block mr-1">ICT학점연계프로젝트인턴십 사업 안내 </p>
                        <img src={fileImage} className="d-inline-block"/>

                        <div>
                            <p className="d-inline-block mr-3 mb-0" style={{color: "#8C8C8C", fontSize: '13px'}}>양희림</p>
                            <p className="d-inline-block mb-0" style={{color: "#8C8C8C", fontSize: '13px'}}>2021-01-09
                                11:10:05</p>
                        </div>
                    </div>

                    <div className="p-3">
                        <p>【 ICT학점연계프로젝트인턴십 사업 안내 】 ○ 사업목적 - 정보통신 관련학과 대학생이 학점 인정을 조건으로 국내/외 기업에서 제안한 ICT관련 직무 중심 인턴십을
                            수행하도록 지원하여 이론과 실무 역량을 겸비한 ICT실무인재 양성 ​ ○ 사업주요내용 - (지원규모) 글로벌 실습생 10명, 국내 실습생 160명 내외 ​ -
                            (지원기간) 글로벌 '21년 상반기 6개월 (3~8월) 국내 '21년 상반기 4개월 (3~6월) - (지원내용) 글로벌(현지인턴십) ㆍ실습생 인턴십 준비금 :
                            왕복항공료, 비자발급 수수료, 의료보험 등 ㆍ실습생 인턴십 수당 (기업부담 ) : 월 $1,700 이상 ㆍ 실습생 인턴십 지원비 : 최대 150 만원 / 월
                            (차등지원) 글로벌(원격인턴십) * COVID-19 팬데믹 지속 시 국내지사 출퇴근 또는 해외기업과의 원격근무 추진 ㆍ인턴십 수당 (기업부담 ) : 월 $1,300
                            이상 ㆍ 인턴십 지원비 : 50 만원 / 월 ㆍ 실습생 주거지원비 : 최대 40만원/월 (조건부) 국내 ㆍ실습생 활동 수당 : 145만원/월 이상 (정부지원금
                            100만원+기업부담금 45만원 이상) ㆍ 실습생 주거지원비 : 최대 40만원/월 (조건부) ㆍ참여대학 별 교과목 이수에 따른 학점인정 ​ - (지원대상) ㆍ참여대학:
                            국내 고등교육법에 따른 대학 , 산업대학 , 교육대학 , 전문대학 등 ㆍ학생 : 참여대학의 정보통신 관련학과에 재학 중인 전공자 , 복수전공 또는 부전공자 *
                            (글로벌 인턴십 과정) 4년제 대학 기준 6학기, 2년제 대학 기준 3학기 이상 이수자 * (국내 인턴십 과정) 4년제 대학 기준 4학기, 2년제 대학 기준 2학기
                            이상 이수자 ​</p>
                    </div>

                    <div className="p-3">
                        <p style={{color: "#6CBACB", fontSize: '14px'}} className="mb-1">첨부파일</p>
                        <img src={fileImage} style={{marginLeft: '5px'}} className="d-inline-block mr-1"/>
                        <p style={{fontSize: '14px'}} className="d-inline-block">ICT학점연계프로젝트인턴십 안내자료.pdf</p>
                    </div>
                </div>

                <ListButton/>
            </Container>
        </div>
    )
}

export function ListButton() {
    let history = useHistory();

    function handleClick() {
        history.goBack();
    }

    return (
        <Row style={{marginBottom: '3rem'}}>
            <Col lg={12} md={12} sm={12}>
                <Button onClick={handleClick} className="float-left btn-sm" style={{
                    backgroundColor: 'white',
                    color: 'black',
                    borderColor: '#D4D4D4',
                    boxShadow: 'none',
                    width: '100px',
                    borderRadius: 0
                }}>목록</Button>
            </Col>
        </Row>
    )
}
