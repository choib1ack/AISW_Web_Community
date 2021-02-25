import React from "react";
import Card from "react-bootstrap/Card";
import defaultImage from "../image/defaultImage.svg"

function ContestInfoCard(props) {
    return (
        <Card style={{width: '100%', marginBottom: '1rem'}} className="text-left">
            <img src={props.image} style={{width: '100%', height:'50%'}}/>
            <Card.Body>
                <Card.Title style={{fontSize: '15px'}}>{props.title}</Card.Title>
                <Card.Subtitle style={{fontSize: '12px', color:'#B8B8B8'}}>{props.host}</Card.Subtitle>

                <Card.Text style={{marginTop: '0.5rem', marginBottom:'0'}}>
                    <span style={{fontSize: '14px', fontWeight:'bold', color:'#6CBACB'}}> D-1<br/></span>
                    <span style={{fontSize: '13px'}}>{props.date}</span>
                </Card.Text>
            </Card.Body>
        </Card>
    )
}
export default ContestInfoCard;
