import React from "react";
import Accordion from "react-bootstrap/Accordion";
import Card from "react-bootstrap/Card";
import {useAccordionToggle} from "react-bootstrap";

function CustomToggle({children, eventKey}) {
    // const decoratedOnClick = useAccordionToggle(eventKey, () =>
    //     console.log('totally custom!'),
    // );
    const decoratedOnClick = useAccordionToggle(eventKey);
    let q_style = {
        padding: '10px',
        borderBottom: '1px solid #D4D4D4'
    }

    let point_style1 = {
        display:'inline',
        fontWeight:'bold',
        color:'#6CBACB',
        fontSize:'18px',
        marginRight:'1rem'
    }
    let point_style2 = {
        display:'inline',
        float:'right',
        fontWeight:'bold',
        color:'#6CBACB',
        fontSize:'18px'
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

function FAQ(props) {
    // props.data
    let data = [
        {Q: '명시되어있지 않은 교양과목을 수강하면 어떻게되나요?', A: '졸업못합니당'},
        {Q: '트랙은 어떻게 선택하는건가요?', A: '3개이상 들으세요!'}
    ];
    let items = [];
    for (let i = 1; i < Object.keys(data).length+1; i++) {
        items.push(
            // <p key={i}>{data[i].Q}/{data[i].A}</p>
            <Card key={i} style={{border:'0px'}}>
                <CustomToggle eventKey={i}>{data[i-1].Q}</CustomToggle>
                <Accordion.Collapse eventKey={i}>
                    <Card.Body style={{fontSize:'13px'}}>{data[i-1].A}</Card.Body>
                </Accordion.Collapse>
            </Card>
        );
    }
    return (
        <div className='FAQ'>
            <Accordion style={{textAlign:'left'}}>
                {/*<Card style={{border:'0px'}}>*/}
                {/*    <CustomToggle eventKey="0">{data[0].Q}</CustomToggle>*/}
                {/*    <Accordion.Collapse eventKey="0">*/}
                {/*        <Card.Body style={{fontSize:'13px'}}>{data[0].A}</Card.Body>*/}
                {/*    </Accordion.Collapse>*/}
                {/*</Card>*/}
                {/*<Card style={{border:'0px'}}>*/}
                {/*    <CustomToggle eventKey="1">{data[1].Q}</CustomToggle>*/}
                {/*    <Accordion.Collapse eventKey="1">*/}
                {/*        <Card.Body style={{fontSize:'13px'}}>{data[1].A}</Card.Body>*/}
                {/*    </Accordion.Collapse>*/}
                {/*</Card>*/}
                {items}
            </Accordion>
        </div>
    );
}

export default FAQ;

