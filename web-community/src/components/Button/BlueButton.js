import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {Link} from "react-router-dom";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import React from "react";

export function BlueButton(props) {
    return (
        <Row>
            <Col>
                <Link to={`${props.type}`}>
                    <Button className="Menu-button blue-button"
                            style={{float: 'right', marginBottom: '3rem'}}>
                        {props.title}
                    </Button>
                </Link>
            </Col>
        </Row>
    );
}
