import Form from "react-bootstrap/Form";
import React, {useEffect, useRef, useState} from "react";
import './Join.css';
import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import classNames from "classnames";
import {useForm} from "react-hook-form";
import axios from 'axios';
import {useDispatch, useSelector} from "react-redux";
import {join} from "../features/userSlice";
import FinishModal from "./FinishModal";

export default function Join() {
    const {register, handleSubmit, watch, errors, setValue} = useForm();
    const password = useRef();
    const phone_number = useRef();
    password.current = watch("password");
    phone_number.current = watch("phone_number");
    const [pwValidate, setPWValidate] = useState(0);
    const [agree, setAgree] = useState(false);

    // redux toolkit
    const user = useSelector(state => state.user.userData)
    const dispatch = useDispatch()

    // 회원가입 완료 모달
    const [modalShow, setModalShow] = useState(false);

    async function sendServer(data) {
        await axios.post("/user/signup",
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data,
            },
        ).then((res) => {
            setModalShow(true)   // 완료 모달 띄우기
            dispatch(join())
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log("에러 발생");
            console.log(errorObject);

            alert("회원가입에 실패하였습니다.") // 실패 메시지
        })
    }

    const onSubmit = (data) => {
        const userData = {
            college_name: data.college,
            department_name: data.department,
            email: data.email,
            gender: data.gender,
            grade: data.grade,
            job: data.job,
            level: 'NOT_SUBSCRIBED',
            name: data.name,
            password: data.password,
            phone_number: data.phone_number,
            roles: 'NOT_PERMITTED',
            student_id: Number(data.student_id),
            university: 'COMMON'
        }

        if (agree) {
            sendServer(userData);   // 백엔드 체크
        } else {
            console.log("동의해주세요.");
        }
    }

    // 비밀번호 유효성 검사
    function checkPassword(password) {
        const num = password.search(/[0-9]/g);
        const eng = password.search(/[a-z]/ig);
        const spe = password.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

        if (password.length < 8 || password.length > 20) {
            setPWValidate(1);
            return false;
        } else if (password.search(/\s/) !== -1) {
            setPWValidate(2);
            return false;
        } else if (num < 0 || eng < 0 || spe < 0) {
            setPWValidate(3);
            return false;
        } else {
            setPWValidate(0);
            return true;
        }
    }

    // 전화번호 유효성 검사
    function checkPhoneNumber(phone) {
        if (/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/.test(phone)) {
            return true;
        }
        return false;
    }

    return (
        <Container className="p-5">
            <FinishModal show={modalShow} link={`/login`}
                         title="회원가입" body="회원가입이 완료되었습니다 !"/>

            <h3 className="font-weight-bold mb-5">
                회원가입
            </h3>

            <Row>
                <Col/>
                <Col sm={12} md={10} lg={8}>
                    <Form onSubmit={handleSubmit(onSubmit)} className="text-left">
                        <Form.Group>
                            <Form.Label>이메일</Form.Label>
                            <Form.Control required type="email" placeholder="E-mail"
                                          name="email" ref={register}/>
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>비밀번호</Form.Label>
                            <Form.Control required type="password" placeholder="Password"
                                          name="password"
                                          ref={register({
                                              validate: (value) => checkPassword(value)
                                          })}
                            />
                        </Form.Group>
                        {
                            errors.password &&
                            errors.password.type === "validate" &&
                            (
                                pwValidate === 1 && (
                                    <p style={{color: 'red', fontSize: 12}}>8자리 ~ 20자리 이내로 입력해주세요.</p>
                                )
                            )
                            || (
                                pwValidate === 2 && (
                                    <p style={{color: 'red', fontSize: 12}}> 비밀번호는 공백 없이 입력해주세요. .</p>
                                )
                            )
                            || (
                                pwValidate === 3 && (
                                    <p style={{color: 'red', fontSize: 12}}>영문, 숫자, 특수문자를 혼합하여 입력해주세요.</p>
                                )
                            )
                        }
                        <Form.Group>
                            <Form.Label>비밀번호 확인</Form.Label>
                            <Form.Control required type="password" placeholder="Confirm Password"
                                          name="password_confirm"
                                          ref={register({validate: (value) => value === password.current})}/>
                        </Form.Group>
                        {
                            errors.password_confirm &&
                            errors.password_confirm.type === "validate" && (
                                <p style={{color: 'red', fontSize: 12}}>비밀번호와 비밀번호 확인이 일치하지 않습니다.</p>
                            )}

                        <Form.Row>
                            <Form.Group sm={9} as={Col}>
                                <Form.Label>이름</Form.Label>
                                <Form.Control required type="text" placeholder="ex) 홍길동"
                                              name="name" ref={register}/>
                            </Form.Group>

                            <Form.Group as={Col} style={{alignSelf: 'center'}}>
                                <Form.Label/>
                                <Form.Row>
                                    <Col style={{textAlign: 'center'}}>
                                        <Form.Check
                                            required
                                            type="radio"
                                            label="남"
                                            name="gender"
                                            value="MALE"
                                            ref={register}
                                        />
                                    </Col>
                                    <Col>
                                        <Form.Check
                                            required
                                            type="radio"
                                            label="여"
                                            name="gender"
                                            value="FEMALE"
                                            ref={register}
                                        />
                                    </Col>
                                </Form.Row>
                            </Form.Group>
                        </Form.Row>

                        <Form.Group>
                            <Form.Label>전화번호</Form.Label>
                            <Form.Control required type="text" placeholder="ex) 010-0000-0000"
                                          name="phone_number"
                                          ref={register({validate: (value) => checkPhoneNumber(value)})}/>
                        </Form.Group>
                        {
                            errors.phone_number &&
                            errors.phone_number.type === "validate" && (
                                <p style={{color: 'red', fontSize: 12}}>유효하지 않는 전화번호입니다.</p>
                            )}

                        <Form.Row>
                            <Form.Group as={Col}>
                                <Form.Label>직업</Form.Label>
                                <Form.Control as="select" name="job" ref={register}>
                                    <option value="STUDENT">재학생</option>
                                    <option value="ADMINISTRATOR">관리자</option>
                                    <option value="COUNCIL">학생회</option>
                                    <option value="FACULTY">교직원</option>
                                </Form.Control>
                            </Form.Group>

                            <Form.Group as={Col}>
                                <Form.Label>학번</Form.Label>
                                <Form.Control required type="text" placeholder="ex) 201533662"
                                              name="student_id"
                                              ref={register({validate: (value) => value.length === 9})}/>
                                {
                                    errors.student_id &&
                                    errors.student_id.type === "validate" && (
                                        <p style={{color: 'red', fontSize: 12, marginTop: '5px'}}>학번이 올바르지 않습니다.</p>
                                    )}
                            </Form.Group>

                            <Form.Group as={Col}>
                                <Form.Label>학년</Form.Label>
                                <Form.Control as="select" default="FRESHMAN"
                                              name="grade" ref={register}>
                                    <option value="FRESHMAN">1학년</option>
                                    <option value="SOPHOMORE">2학년</option>
                                    <option value="JUNIOR">3학년</option>
                                    <option value="SENIOR">4학년</option>
                                </Form.Control>
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col}>
                                <Form.Label>단과대학</Form.Label>
                                <Form.Control as="select"
                                              name="college" ref={register}>
                                    <option value="IT_CONVERGENCE">IT 융합대학</option>
                                    <option value="BUSINESS_ADMINISTRATION">사회과학대학</option>
                                    <option value="SOCIAL_SCIENCE">인문대학</option>
                                    <option value="HUMANITIES">법학대학</option>
                                    <option value="BIO_NANO">바이오나노대학</option>
                                    <option value="ORIENTAL_MEDICINE">한의과대학</option>
                                    <option value="ARTS_PHYSICAL">예술/체육대학</option>
                                </Form.Control>
                            </Form.Group>
                            <Form.Group as={Col}>
                                <Form.Label>학과</Form.Label>
                                <Form.Control as="select"
                                              name="department" ref={register}>
                                    <option value="SOFTWARE">소프트웨어학과</option>
                                    <option value="AI">인공지능학과</option>
                                </Form.Control>
                            </Form.Group>
                        </Form.Row>

                        <Form.Group style={{textAlign: 'right', marginTop: '50px'}}>
                            <Form.Check type="checkbox" label="개인정보 수집에 동의합니다." onClick={() => setAgree(!agree)}/>
                        </Form.Group>

                        <div style={{display: 'flex', alignItems: 'flex-end', justifyContent: 'flex-end'}}>
                            <Button className={classNames("select-btn", "off")} style={{width: '80px'}}>취소</Button>
                            <Button className={classNames("select-btn", "on")} style={{width: '80px'}}
                                    type="submit">확인</Button>
                        </div>
                    </Form>
                </Col>
                <Col/>
            </Row>

        </Container>
    );
}
