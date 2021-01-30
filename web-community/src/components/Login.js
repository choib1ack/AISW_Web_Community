import Form from "react-bootstrap/Form";
import React from "react";
import './Login.css';
import Container from "react-bootstrap/Container";
import {Link} from "react-router-dom";

export default function Login() {
    return (
        <Container className="Login">
            <h3 className="font-weight-bold mb-5">
                로그인
            </h3>
            <h6 className="mb-4" style={{color: '#D4D4D4'}}>
                가천대학교 AI&소프트웨어학부에 오신 걸 환영합니다.
            </h6>

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

            <div>
                <p className="small d-inline-block" style={{color: '#8C8C8C'}}>
                    비밀번호 찾기 /
                </p>

                <Link to={`/join`}>
                    <p className="small d-inline-block ml-3" style={{color: '#8C8C8C'}}>
                        회원가입
                    </p>
                </Link>
            </div>

        </Container>
    );
}
