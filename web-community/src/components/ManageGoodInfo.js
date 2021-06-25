import Container from "react-bootstrap/Container";
import Title from "./Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import React, {useState} from "react";
import programmersImage from "../siteImages/programmers.png";
import baekjoonImage from "../siteImages/baekjoon.png";
import lifeCodingImage from "../siteImages/life_coding.png";
import addWebPageImage from "../image/add_webpage_btn.svg";
import Button from "react-bootstrap/Button";
import BorderButton from "./Button/BorderButton";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";

function ManageGoodInfo({match}) {
    const [show, setShow] = useState(false);

    const [imgBase64, setImgBase64] = useState(""); // 파일 base64
    const [imgFile, setImgFile] = useState(null);	//파일

    const handleShow = () => setShow(true);


    let style = {
        border: '1px solid #C0C0C0',
        width: '100%'
    }
    let add_btn_style = {
        border: '1px solid #E8E8E8',
        width: '100%',
        cursor: 'pointer'
    }

    const handleChangeFile = (event) => {
        let reader = new FileReader();

        reader.onloadend = () => {
            // 2. 읽기가 완료되면 아래코드가 실행됩니다.
            const base64 = reader.result;
            if (base64) {
                setImgBase64(base64.toString()); // 파일 base64 상태 업데이트
            }
        }
        if (event.target.files[0]) {
            reader.readAsDataURL(event.target.files[0]); // 1. 파일을 읽어 버퍼에 저장합니다.
            setImgFile(event.target.files[0]); // 파일 상태 업데이트
        }
    }

    const modalClose = () => {
        setImgBase64("");
        setImgFile(null);
        setShow(false);
    }

    const modalSubmit = () => {
        // 서버에 이미지, 데이터 전송
        modalClose();
    }

    return (
        <div className='Manager'>
            <Container>
                <Title text='관리자' type='1'/>
                <Title text='유용한 사이트' type='2'/>
                <Title text='코딩테스트 준비' type='3'/>
                <Row>
                    <Col lg={2} md={2} sm={2}>
                        <img src={programmersImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={baekjoonImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={lifeCodingImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={addWebPageImage} style={add_btn_style} onClick={handleShow}/>
                    </Col>
                </Row>
                <Title text='온라인 강의' type='3'/>
                <Row>
                    <Col lg={2} md={2} sm={2}>
                        <img src={baekjoonImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={programmersImage} style={style}/>
                    </Col>
                    <Col lg={2} md={2} sm={2}>
                        <img src={addWebPageImage} style={add_btn_style}/>
                    </Col>
                </Row>
                <Row style={{marginTop: '3rem'}}>
                    <Col>
                        <BorderButton content='+ 새 카테고리 추가하기'
                        />
                    </Col>
                </Row>
            </Container>

            <Modal show={show} onHide={modalClose}>
                <Modal.Header closeButton>
                    <Modal.Title>새 사이트 추가</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="">
                            <Form.Label>사이트 명<span style={{color:"#FF0000"}}> *</span></Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="">
                            <Form.Label>사이트 상세(선택)</Form.Label>
                            <Form.Control type="text" placeholder=""/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="">
                            <Form.Label>URL<span style={{color:"#FF0000"}}> *</span></Form.Label>
                            <Form.Control type="text" placeholder="http://"/>
                        </Form.Group>
                        <Form.Group controlId="formFileSm" className="mb-3">
                            <div><Form.Label>
                                대표 이미지 (500x500)<span style={{color:"#FF0000"}}> *</span>
                            </Form.Label></div>
                            {imgBase64 == "" ? null : <div><img src={imgBase64} style={{width:"50%", height : "50%"}}/></div>
                            }
                            <input type="file" name="imgFile" id="imgFile" onChange={handleChangeFile}/>
                            {/*<Form.Control type="file" size="sm" onChange={handleChangeFile}/>*/}
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={modalClose}>
                        닫기
                    </Button>
                    <Button variant="primary" onClick={modalSubmit}>
                        추가
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}
export default ManageGoodInfo;
