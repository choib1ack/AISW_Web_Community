import Form from "react-bootstrap/Form";
import React, {useRef} from "react";
import './Login.css';
import Container from "react-bootstrap/Container";
import {Link, useHistory} from "react-router-dom";
import {useForm} from "react-hook-form";
import axios from "axios";

export default function Login(props) {
    const {register, handleSubmit, watch, errors, setValue} = useForm();
    // const email = useRef();
    // const password = useRef();
    // email.current = watch("email");
    // password.current = watch("password");
    const history = useHistory();

    async function getLoginUser(data) {
        await axios.get(`/user/login?email=${data.email}&password=${data.password}`).then((res) => {
            if (res.data.data == null) {
                alert("회원이 존재하지 않습니다.")
            } else {
                // dispatch(login(res.data.data))   // 리덕스에 로그인한 유저 정보 저장
                history.push('/')   // 홈으로 가기
            }
        }).catch(error => {
            alert("로그인에 실패하였습니다.") // 로그인 실패 메시지
        })
    }

    const onSubmit = (data) => {
        if (data.email === "") {
            alert("이메일을 입력해주세요.")
        } else if (data.password === "") {
            alert("비밀번호를 입력해주세요.")
        } else {
            getLoginUser(data);
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
