import Form from "react-bootstrap/Form";
import React, {useEffect, useState} from "react";
import './Join.css';
import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import classNames from "classnames";
import {useForm} from "react-hook-form";
import axios from 'axios';

export default function Join({match}) {
    const [agree, setAgree] = useState(false);
    const {register, handleSubmit, watch, errors, setValue} = useForm();

    async function test(data) {
        // try {
        //     let res = await axios.get('http://localhost:8080/user')
        //     console.log(res);
        // } catch (e) {
        //     console.log(e.response) // undefined
        // }

        await axios.post("http://localhost:8080/user",
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data
            }
        ).then((res) => {
            console.log(res)
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log("에러");
            console.log(errorObject);
        })
    }

    const onSubmit = data => {
        const object1 = {
            name: 'Test04',
            email: 'Test04@gmail.com',
            password: 'pppoop22',
            phone_number: '010-2222-2222',
            grade: 'SENIOR',
            student_id: 202222230,
            level: 'NOT_SUBSCRIBED',
            job: 'STUDENT',
            gender: 'MALE',
            university: 'GLOBAL',
            college: 'IT_CONVERGENCE',
            department: 'SOFTWARE'
        }
        data = object1;
        if (agree) {
            console.log(data);
            test(data);
        } else {
            console.log("동의해주세요.");
        }
    }

    return (
        <Container className="p-5">
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
                                          name="password" ref={register}/>
                        </Form.Group>

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
                                            name="formHorizontalRadios"
                                            ref={register({name: 'gender', value: 1})}
                                        />
                                    </Col>
                                    <Col>
                                        <Form.Check
                                            required
                                            type="radio"
                                            label="여"
                                            name="formHorizontalRadios"
                                            ref={register({name: 'gender', value: 2})}
                                        />
                                    </Col>
                                </Form.Row>
                            </Form.Group>
                        </Form.Row>

                        <Form.Group>
                            <Form.Label>전화번호</Form.Label>
                            <Form.Control required type="text" placeholder="ex) 010-0000-0000"
                                          name="phone_number" ref={register}/>
                        </Form.Group>

                        <Form.Row>
                            <Form.Group as={Col}>
                                <Form.Label>직업</Form.Label>
                                <Form.Control as="select" name="job" ref={register}>
                                    <option value="재학생">재학생</option>
                                    <option value="졸업생">졸업생</option>
                                    <option value="학생회">학생회</option>
                                    <option value="직원">직원</option>
                                </Form.Control>
                            </Form.Group>

                            <Form.Group as={Col}>
                                <Form.Label>학번</Form.Label>
                                <Form.Control required type="text" placeholder="ex) 201533662"
                                              name="student_id" ref={register}/>
                            </Form.Group>

                            <Form.Group as={Col}>
                                <Form.Label>학년</Form.Label>
                                <Form.Control as="select" default="해당없음"
                                              name="grade" ref={register}>
                                    <option value="">해당없음</option>
                                    <option value={1}>1학년</option>
                                    <option value={2}>2학년</option>
                                    <option value={3}>3학년</option>
                                    <option value={4}>4학년</option>
                                </Form.Control>
                            </Form.Group>
                        </Form.Row>

                        <Form.Row>
                            <Form.Group as={Col}>
                                <Form.Label>단과대학</Form.Label>
                                <Form.Control required type="text" placeholder="ex) IT융합대학"
                                              name="college" ref={register}/>
                            </Form.Group>
                            <Form.Group as={Col}>
                                <Form.Label>학과</Form.Label>
                                <Form.Control required type="text" placeholder="ex) 소프트웨어학과"
                                              name="department" ref={register}/>
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
