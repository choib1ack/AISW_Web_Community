import React, {useState} from "react";
import Button from "react-bootstrap/Button";

export default function SubjectList() {
    const subject_list = [
        "컴퓨터 프로그래밍", "웹 프로그래밍", "소프트웨어수학", "소프트웨어생태계", "소프트웨어 구현패턴",
        "로봇공학", "자료구조", "객체지향 프로그래밍", "운영체제", "확률통계", "네트워크", "알고리즘",
        "데이터베이스", "모바일 프로그래밍", "소프트웨어 공학", "소프트웨어 산업세미나", "컴퓨터 그래픽스", "컴퓨터구조",
        "데이터과학", "센서와 무선네트워크", "인공지능개론", "머신러닝", "딥러닝", "드론과 로보틱스", "임베디드 시스템",
        "VR과 AR", "멀티미디어 및 실습", "HCI", "클라우드컴퓨팅", "컴퓨터비전"
    ]


    let subject_btn_list =[];
    for (let i = 0; i <subject_list.length; i++) {
        subject_btn_list.push(
            <SubjectButton
                key={i}
                sub_name={subject_list[i]}
            />,
        );
    }
    return(
        <div>
            {subject_btn_list}
        </div>
    );

}

function SubjectButton(props){
    const [active, setActive] = useState(false);

    const changeState = () => {
        setActive(!active);
    }

    let btnStyle = {
        float: 'left',
        margin: '0.5rem',
        border: active ? '1px solid #0472FD' : '1px solid #d5d5d5',
        outline: 'none',
        boxShadow: 'none',
        radius:'0',
        backgroundColor: active ? '#e7f1ff' :'#ffffff',
        color: active ? '#0472FD' : '#d5d5d5'
    }
    return (
        <Button style={btnStyle} onClick={()=>changeState()}>{props.sub_name}</Button>
    );
}