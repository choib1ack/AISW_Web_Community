import React from "react";
import Container from "react-bootstrap/Container";
import AICur from "../image/AI_curriculum.svg";
import SWCur from "../image/SW_curriculum.svg";
import culturalStudies from "../image/cultural_studies.svg";
import Title from "./Title";
import FAQ from "./FAQ";

function DeptInfo() {
    let textBox = {
        textAlign:'left',
        padding:'20px',
        lineHeight:'200%',
        border:'1px solid #D4D4D4'
    }
    return (
        <div className="DeptInfo">
            <Container >

                <Title text='자주 묻는 질문' type='1'/>
                <FAQ/>

                <Title text='교육과정' type='1'/>
                <Title text='AI 전공' type='2'/>
                <img src = {AICur} style={{width:'80%'}}/>
                <Title text='소프트웨어 전공' type='2'/>
                <img src = {SWCur} style={{width:'80%'}}/>

                <Title text='졸업요건' type='1'/>
                <Title text='기본요건' type='2'/>
                <p style={textBox}>
                    - 120학점 이수<br/>
                    - 2017학년도 이후 입학생은 동일 트랙에서 트랙제 교과목 4개 이상을 필수적으로 이수. 단 4개 교과목 중 1개는 다른 트랙에서 수강 가능. <br/>
                    &nbsp;&nbsp; 예를 들어 한 트랙에서 3개, 다른 트랙에서 1개도 인정함.
                </p>

                <Title text='수강 가능 교양과목' type='2'/>
                <img src = {culturalStudies} style={{width:'100%'}}/>
                <p style={textBox}>
                    - 기초, 융합교양 과목은 추천과목/선택가능과목 내에서 수강 가능<br/>
                    - 기초교양18+융합6+일반2=26학점 외의 교양 교과목 수강은 위 표에 명시된 교양 교과목 내에서만 수강 인정함. 그 외 교양과목은 졸업인증시 불인정<br/>
                    - 교양수강은 기본적으로 학사안내의 졸업요건과 학과의 졸업요건을 모두 만족 해야함<br/>
                    - 기초교양: 학번별 기초교양 교과목 관련 문의사항은 교무처 학사팀 담당자 또는 교양대학에 문의<br/>
                    - 일반선택: 일반선택 교양은 “영역”과 관계 없이 선택가능 교과목 중에서 수강<br/>
                    - 교양과목이 개편된 경우, 수강한 학기의 기준에 따라 이수인정됨. <br/>
                      &nbsp;&nbsp;예) 건축문화사가 융합교양으로 개설된 학기에 수강했으면 융합교양으로 인정하고,
                    일반선택으로 개설된 학기에 수강했으면 일반선택으로 인정함.<br/>
                    - 위 내용은 학번에 관계없이 2019년부터 모두 적용됨.<br/>
                    - 위 추천/선택 교과목에는 없으나 본인 학번의 교육과정에는 있는 교과목을 수강한 경우도 인정<br/>
                    &nbsp;&nbsp;&nbsp;예를 들어 위의 추천/선택수강 과목에 없는 "심리학개론"을 수강한 2014학번은 수강인정함<br/>
                    &nbsp;&nbsp;&nbsp;위 추천 교과목 목록에는 없으나 이전 2017년도 공지된 학과교양규정에 명시된 교과목을 수강한 경우도 인정함<br/>
                </p>
                <Title text='기타 졸업요건' type='2'/>

                <p style={textBox}>
                    - 사회봉사 이수<br/>
                    - 어학성적 : 토익 700이상, 토익스피킹 lv5 이상, 텝스? ~<br/>
                    - 산학과제 수행 (졸업작품 이수를 위해 의무적으로 1회 이상 수행해야 함.)<br/>
                </p>


            </Container>
        </div>
    );
}

export default DeptInfo;