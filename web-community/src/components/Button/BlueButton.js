import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {Link} from "react-router-dom";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import React from "react";

export function BlueButton(props) {
    return (
        <Row>
            <Col lg={12} md={12} sm={12}>
                <Link to={`${props.match.url}/${props.type}`}>
                    <Button className={classNames("select-btn", "on")}
                            style={{float: 'right'}}>
                        {props.title}
                    </Button>
                </Link>
            </Col>
        </Row>
    );
}
