import React, {useEffect, useState} from "react";
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";
import {useAccordionToggle} from "react-bootstrap";
import Container from "react-bootstrap/Container";
import Title from "./Title";
import axios from "axios";
import {useDispatch} from "react-redux";
import {setActiveTab} from "../features/menuSlice";
import Loading from "./Loading";


function FAQ(props) {
    // props.data
    // let data = [
    //     {Q: '명시되어있지 않은 교양과목을 수강하면 어떻게되나요?', A: '졸업못합니당'},
    //     {Q: '트랙은 어떻게 선택하는건가요?', A: '3개이상 들으세요!'}
    // ];
    // let items = [];
    // for (let i = 1; i < Object.keys(data).length + 1; i++) {
    //     items.push(
    //         // <p key={i}>{data[i].Q}/{data[i].A}</p>
    //         <Card key={i} style={{border: '0px'}}>
    //             <CustomToggle eventKey={i}>{data[i - 1].Q}</CustomToggle>
    //             <Accordion.Collapse eventKey={i}>
    //                 <Card.Body style={{fontSize: '13px'}}>{data[i - 1].A}</Card.Body>
    //             </Accordion.Collapse>
    //         </Card>
    //     );
    // }

    const [faqData, setFaqData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const active_change_dispatch = useDispatch();
    active_change_dispatch(setActiveTab(5));

    useEffect(() => {
        const fetchFaqData = async () => {
            try {
                setError(null);
                setLoading(true);

                await axios.get("/faq")
                    .then((res)=>{
                        setFaqData(res.data.data);
                    });

            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchFaqData();
    }, []);

    if (loading) return <Loading/>;
    if (error) return (
        <p>에러가 발생했습니다{error.toString()}</p>
    );
    if (!faqData) return null;

    return (
        <div className="FAQ">
            <Container>
                <Title text='FAQ' type='1'/>
                {console.log(faqData)}
                {faqData.length > 0 ? faqData.map((data, index) => (
                    <FaqItem
                        index={index}
                        question={data.question}
                        answer={data.answer}
                    />
                )) : <p>데이터가 없습니다.</p>}

            </Container>
        </div>
    );
}
export default FAQ;

function FaqItem({index, question, answer}){

    return(
        <Accordion style={{textAlign: 'left'}}>
            <Card key={index+1} style={{border: '0px'}}>
                <CustomToggle eventKey={index+1}>{question}</CustomToggle>
                <Accordion.Collapse eventKey={index+1}>
                    <Card.Body style={{fontSize: '13px'}}>{answer}</Card.Body>
                </Accordion.Collapse>
            </Card>
        </Accordion>
    )
}

function CustomToggle({children, eventKey}) {
    // const decoratedOnClick = useAccordionToggle(eventKey, () =>
    //     console.log('totally custom!'),
    // );
    const decoratedOnClick = useAccordionToggle(eventKey
        ,(e) =>{
            var item = e.target.parentNode.children[0].children[1];
            if(item.innerText.includes('+',0)){
                item.innerText = item.innerText.replace('+', '-');
            }
            else{
                item.innerText = item.innerText.replace('-', '+');
            }
        }
    );
    let q_style = {
        padding: '10px',
        borderBottom: '1px solid #D4D4D4'
    }

    let point_style1 = {
        display: 'inline',
        fontWeight: 'bold',
        color: '#0472FD',
        fontSize: '18px',
        marginRight: '1rem'
    }
    let point_style2 = {
        display: 'inline',
        float: 'right',
        fontWeight: 'bold',
        color: '#0472FD',
        fontSize: '18px'
    }

    return (
        <div
            type="button"
            style={q_style}
            onClick={decoratedOnClick}
        >
            <p style={point_style1}>Q.</p>
            {children}
            <p style={point_style2}>+</p>
        </div>
    );
}
