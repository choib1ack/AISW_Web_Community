import Card from "react-bootstrap/Card";
import placeImage from "../icon/place.svg";
import monitorImage from "../icon/monitor.svg";
import Button from "react-bootstrap/Button";
import React from "react";

function JobCard(props) {
    let card_style={
        cursor:'pointer',
        marginBottom:'10px'
    }
    let detail_info={
        display: props.detail ? '' : 'none'
    }
    return (
        <Card className="text-left flex-row" style={card_style}>
            <img src={props.image} style={{height: "70px", margin: "0px 30px"}}
                 className="align-self-center"/>

            <Card.Body>
                <Card.Title style={{fontSize: '14px'}}>{props.title}</Card.Title>
                <Card.Subtitle style={{fontSize: '12px'}} className="mb-2 text-muted">{props.host}</Card.Subtitle>

                <Card.Text className="mb-2" style={detail_info}>
                    <div className="d-inline-block">
                        <img src={placeImage} style={{width: "15px", height: "15px", marginRight: "5px"}}/>
                        <p className="d-inline-block mr-3 mb-0" style={{fontSize: '12px'}}>
                            {props.place}
                        </p>
                    </div>
                    <div className="d-inline-block">
                        <img src={monitorImage} style={{width: "11px", height: "11px", marginRight: "5px"}}/>
                        <p className="d-inline-block mb-0" style={{fontSize: '12px'}}>
                            {props.position}
                        </p>
                    </div>
                </Card.Text>
                <CategoryButton title="서버/백앤드"/>
                <CategoryButton title="Spring"/>
            </Card.Body>
        </Card>
    );
}export default JobCard;

function CategoryButton(props) {
    let btnStyle = {
        fontSize: '12px',
        float: 'left',
        margin: '0.3rem',
        border: '0',
        outline: 'none',
        boxShadow: 'none',
        backgroundColor: '#EFF7F9',
        color: '#6CBACB'
    }
    return (
        <Button style={btnStyle}>{props.title}</Button>
    );
};
