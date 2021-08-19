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
import FinishModal from "./FinishModal";
import {useHistory, useLocation} from "react-router-dom";

export default function Join() {
    const {register, handleSubmit, watch, errors, setValue} = useForm();
    const phone_number = useRef();
    phone_number.current = watch("phone_number");
    const [agree, setAgree] = useState(false);
    const history = useHistory();

    const [modalShow, setModalShow] = useState(false);
    const location = useLocation();

    function join(data) {
        axios.post("/user/signup", {data},
        ).then((res) => {
            setModalShow(true)   // 완료 모달 띄우기
        }).catch(error => {
            alert("회원가입에 실패하였습니다.") // 실패 메시지
        })
    }

    const onSubmit = (data) => {
        const userData = {
            // 'college_name': data.college,
            // 'department_name': data.department,
            'email': location.state.google_data.profileObj.email,
            'gender': data.gender,
            'grade': data.grade,
            'name': location.state.google_data.profileObj.familyName,
            'provider': location.state.google_data.tokenObj.idpId,
            'provider_id': location.state.google_data.profileObj.googleId,
            'phone_number': data.phone_number.replaceAll('-', ''),
            'role': `ROLE_${location.state.account_role}`,   // GENERAL, STUDENT
            'student_id': data.student_id,
            'university': 'COMMON',
            'picture': location.state.google_data.profileObj.picture
        }

        if (agree) {
            join(userData);
        } else {
            alert("개인정보 수집에 동의해주세요.");
        }
    }

    // 전화번호 유효성 검사
    function checkPhoneNumber(phone) {
        return /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/.test(phone);
    }

    return (
        <Container className="p-5">
            <FinishModal show={modalShow} link={`/`}
                         title="회원가입" body="회원가입이 완료되었습니다 !"/>

            <h3 className="font-weight-bold mb-5">
                회원가입
            </h3>

            <Row>
                <Col/>
                <Col sm={12} md={10} lg={8}>
                    <Form onSubmit={handleSubmit(onSubmit)} className="text-left">
                        <Form.Row>
                            <Form.Group sm={9} as={Col}>
                                <Form.Label>전화번호</Form.Label>
                                <Form.Control required type="text" placeholder="ex) 010-0000-0000"
                                              name="phone_number"
                                              ref={register({validate: (value) => checkPhoneNumber(value)})}/>
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

                            {/*<Form.Group as={Col}>*/}
                            {/*    <Form.Label>학번</Form.Label>*/}
                            {/*    <Form.Control required type="text" placeholder="ex) 201533662"*/}
                            {/*                  name="student_id"*/}
                            {/*                  ref={register({validate: (value) => value.length === 9})}/>*/}
                            {/*    {*/}
                            {/*        errors.student_id &&*/}
                            {/*        errors.student_id.type === "validate" && (*/}
                            {/*            <p style={{color: 'red', fontSize: 12, marginTop: '5px'}}>학번이 올바르지 않습니다.</p>*/}
                            {/*        )}*/}
                            {/*</Form.Group>*/}

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

                        {/*<Form.Row>*/}
                        {/*    <Form.Group as={Col}>*/}
                        {/*        <Form.Label>단과대학</Form.Label>*/}
                        {/*        <Form.Control as="select"*/}
                        {/*                      name="college" ref={register}>*/}
                        {/*            <option value="IT_CONVERGENCE">IT 융합대학</option>*/}
                        {/*            <option value="BUSINESS_ADMINISTRATION">사회과학대학</option>*/}
                        {/*            <option value="SOCIAL_SCIENCE">인문대학</option>*/}
                        {/*            <option value="HUMANITIES">법학대학</option>*/}
                        {/*            <option value="BIO_NANO">바이오나노대학</option>*/}
                        {/*            <option value="ORIENTAL_MEDICINE">한의과대학</option>*/}
                        {/*            <option value="ARTS_PHYSICAL">예술/체육대학</option>*/}
                        {/*        </Form.Control>*/}
                        {/*    </Form.Group>*/}
                        {/*    <Form.Group as={Col}>*/}
                        {/*        <Form.Label>학과</Form.Label>*/}
                        {/*        <Form.Control as="select"*/}
                        {/*                      name="department" ref={register}>*/}
                        {/*            <option value="SOFTWARE">소프트웨어학과</option>*/}
                        {/*            <option value="AI">인공지능학과</option>*/}
                        {/*        </Form.Control>*/}
                        {/*    </Form.Group>*/}
                        {/*</Form.Row>*/}

                        <Form.Group style={{textAlign: 'right', marginTop: '50px'}}>
                            <Form.Check type="checkbox" label="개인정보 수집에 동의합니다." onClick={() => setAgree(!agree)}/>
                        </Form.Group>

                        <div style={{display: 'flex', alignItems: 'flex-end', justifyContent: 'flex-end'}}>
                            <Button className={classNames("select-btn", "off")} style={{width: '80px'}}
                                    onClick={() => history.push('/')}
                            >취소</Button>
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
