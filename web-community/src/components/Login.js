import Form from "react-bootstrap/Form";
import React, {useRef} from "react";
import './Login.css';
import Container from "react-bootstrap/Container";
import {Link, useHistory} from "react-router-dom";
import {useForm} from "react-hook-form";
import {useDispatch, useSelector} from "react-redux";
import {login} from "../features/userSlice";

export default function Login() {
    const {register, handleSubmit, watch, errors, setValue} = useForm();
    const email = useRef();
    const password = useRef();
    email.current = watch("email");
    password.current = watch("password");
    const history = useHistory();

    // redux toolkit
    const user = useSelector(state => state.user)
    const dispatch = useDispatch()

    const onSubmit = (data) => {
        console.log("store: " + user.userData.email + " " + user.userData.password)

        // 리덕스 스토어에 회원정보가 등록되어 있는 경우
        if (user.userData.email === data.email && user.userData.password === data.password) {
            dispatch(login())
            history.push('/')   // 홈으로 가기
        } else {
            alert("이메일과 비밀번호를 확인해주세요.")
        }
    }

    return (
        <Container className="Login">
            <h3 className="font-weight-bold mb-5">
                로그인
            </h3>
            <h6 className="mb-4" style={{color: '#D4D4D4'}}>
                가천대학교 AI&소프트웨어학부에 오신 걸 환영합니다.
            </h6>

            <Form onSubmit={handleSubmit(onSubmit)} className="mt-2 mb-4">
                <Form.Group controlId="formBasicEmail">
                    <Form.Control className="Login-form-control" type="email" placeholder="이메일"
                                  name="email" ref={register}/>
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Control className="Login-form-control" type="password" placeholder="비밀번호"
                                  name="password" ref={register}/>
                </Form.Group>

                <button type="submit" className="Menu-button blue-button Login-form-control">
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
