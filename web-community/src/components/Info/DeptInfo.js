import React, {useEffect, useRef} from "react";
import AICur from "../../image/AI_curriculum.svg";
import SWCur from "../../image/SW_curriculum.svg";
import Title from "../Title";
import Table from "react-bootstrap/Table";
import {useHistory} from "react-router-dom";
import './DeptInfo.css';
import {useDispatch} from "react-redux";
import {setActiveTab} from "../../features/menuSlice";

function DeptInfo({match}) {

    const active_change_dispatch = useDispatch();
    active_change_dispatch(setActiveTab(3));

    let textBox = {
        textAlign: 'left',
        padding: '20px',
        lineHeight: '200%',
        border: '1px solid #D4D4D4'
    }

    let history = useHistory();
    const ToLink = (url) => {
        history.push(url);
    }

    const graduation = useRef(); // 졸업요건 위치

    useEffect(()=>{
        if(match.path === "/GraduateCondition")
        window.scrollTo(0, graduation.current.offsetTop);

        // 부드럽게 스크롤 해보려 했는데 안되네,,
        // window.scrollTo({left: 0,
        //     top: graduation.current.offsetTop,
        //     behavior: 'smooth' });

     }, [])


    return (
        <div className="DeptInfo">
            <Title text='교육과정' type='1'/>
            <Title text='AI 전공' type='2'/>
            <img src={AICur} style={{width: '80%'}}/>
            <Title text='소프트웨어 전공' type='2'/>
            <img src={SWCur} style={{width: '80%'}}/>

            <p ref={graduation}></p>
            <Title text='졸업요건' type='1'/>
            <Title text='기본요건' type='2'/>
            <p style={textBox}>
                - 120학점 이수<br/>
                - 필수 전공 과목 이수 <span className="link-underline"
                                    onClick={() => ToLink("/Booklet")}>[학번별 학사요람 확인하기]</span> <br/>
                - 필수 교양 과목 이수 <span className="link-underline"
                                    onClick={() => ToLink("/EssentialElective")}>[학번별 필수 교양 확인하기]</span> <br/>
                - 17학번부터 트렉제 적용. 동일 트랙에서 3개 교과목 이상 이수 필수. (단 4개 교과목 중 1개는 다른 트랙에서 수강 가능) <br/>
                - 이 외 학점은 추천과목/선택가능과목 내에서 수강 가능 <a href="https://sw.gachon.ac.kr/cms/?p=17" target="_blank"
                                                   className="link-underline" > [수강 가능 교양 과목
                확인하기] </a>
            </p>
            <p style={{textAlign: "left"}}>

            </p>

            <Title text='어학성적' type='2'/>

            <Table style={{width: "50%"}}>
                <thead>
                <tr>
                    <th style={{width: "50%"}}>어학 명칭</th>
                    <th style={{width: "50%", textAlign: "center"}}>인증 능력</th>
                </tr>
                </thead>
                <tbody className="english_table type2">
                <tr>
                    <td>TOEIC</td>
                    <td style={{textAlign: "center", paddingLeft: "0px"}}>700점 이상</td>
                </tr>
                <tr>
                    <td>TOEIC Speaking</td>
                    <td style={{textAlign: "center"}}>130점(Lv.6) 이상</td>
                </tr>
                <tr>
                    <td>TOEFL(iBT)</td>
                    <td style={{textAlign: "center"}}>80점 이상</td>
                </tr>
                <tr>
                    <td>OPIc</td>
                    <td style={{textAlign: "center"}}>IM2 이상</td>
                </tr>
                <tr>
                    <td>중국어능력시험(HSK)</td>
                    <td style={{textAlign: "center"}}>HSK 3급 이상</td>
                </tr>
                </tbody>
            </Table>
            <p style={{textAlign: "left", marginBottom: "5rem", color: "dimgrey"}}>( * 목록 중 1개 이상 필수 )</p>

            {/*<p style={textBox}>*/}
            {/*    - 사회봉사 이수<br/>*/}
            {/*    - 어학성적 : 토익 700이상, 토익스피킹 lv5 이상, 텝스? ~<br/>*/}
            {/*    - 산학과제 수행 (졸업작품 이수를 위해 의무적으로 1회 이상 수행해야 함.)<br/>*/}
            {/*</p>*/}


            {/*<Title text='자주 묻는 질문' type='1'/>*/}
            {/*<FAQ/>*/}
            {console.log("여기")}
        </div>
    );
}

export default DeptInfo;
