import {useHistory} from "react-router-dom";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import React from "react";

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
