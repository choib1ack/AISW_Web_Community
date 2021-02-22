import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {Link} from "react-router-dom";
import Button from "react-bootstrap/Button";
import classNames from "classnames";
import React from "react";

export function WriteButton(props) {
    return (
        <Row>
            <Col lg={12} md={12} sm={12}>
                <Link to={`${props.match.url}/${props.type}`}>
                    <Button className={classNames("select-btn", "on")}
                            style={{float: 'right'}}>
                        글쓰기
                    </Button>
                </Link>
            </Col>
        </Row>
    );
}
