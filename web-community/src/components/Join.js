import Form from "react-bootstrap/Form";
import React from "react";
import './Join.css';
import Container from "react-bootstrap/Container";
import {Link} from "react-router-dom";
import Col from "react-bootstrap/Col";

export default function Join({match}) {
    return (
        <Container className="Join">
            <h3 className="font-weight-bold mb-5">
                회원가입
            </h3>

            <Form className="mt-2 mb-4">
                <Form.Group controlId="formBasicEmail">
                    <Form.Control className="Login-form-control" type="email" placeholder="아이디"/>
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Control className="Login-form-control" type="password" placeholder="패스워드"/>
                </Form.Group>

                <button className="Menu-button blue-button Login-form-control">
                    로그인
                </button>
            </Form>

        </Container>
    );
}