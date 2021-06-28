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
import axios from "axios";
import {useForm} from "react-hook-form";

function ManageGoodInfo({match}) {
    const [show, setShow] = useState(false);

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
                        <img src={addWebPageImage} style={add_btn_style} onClick={handleShow}/>
                    </Col>
                </Row>
                <Row style={{marginTop: '3rem'}}>
                    <Col>
                        <BorderButton content='+ 새 카테고리 추가하기'
                        />
                    </Col>
                </Row>
            </Container>

            <AddSiteModal
                show={show}
                setShow={setShow}
            />

        </div>
    );
}
export default ManageGoodInfo;

function AddSiteModal(props){
    const [imgBase64, setImgBase64] = useState(""); // 파일 base64
    const [imgFile, setImgFile] = useState(null);	//파일
    const [siteInfo, setSiteInfo] = useState({site_name:"", site_detail:"", site_url:""});	//파일

    const modalClose = () => {
        setImgBase64("");
        setImgFile(null);
        props.setShow(false);
    }

    const modalSubmit = () => {
        // 서버에 이미지, 데이터 전송
        // 수정인지 신규인지 확인 후 post or update
        modalClose();
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

    const handleInputChange = (event) =>{
        const target = event.target;
        const value = target.value;
        const name = target.name;
        setSiteInfo({
            ...siteInfo,
            [name]: value
        });
        // console.log(siteInfo);
    }

    const handleSubmit = (event) => {
        console.log(siteInfo);
        let site_info = {
            "content": siteInfo.site_detail,
            "information_category": "CODINGTEST",
            "link_url": siteInfo.site_url,
            "name": siteInfo.site_name,
            "publish_status": true
        }
        console.log(site_info);
        sendData(site_info);
    }

    async function sendData(data) {
        await axios.post("/site",
            {
                headers: {
                    "Content-Type": `application/json`
                },
                data,
            },
        ).then((res) => {
            console.log(res)
            alert("새 사이트 등록완료!") // 실패 메시지
            // setModalShow(true)   // 완료 모달 띄우기
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log("에러 발생 (새 사이트 등록)");
            console.log(errorObject);
            alert("새 사이트 등록에 실패하였습니다."); // 실패 메시지
        })
    }


    return (
        <div className="AddSiteModal">
            <Modal show={props.show} onHide={modalClose}>
                <Modal.Header closeButton>
                    <Modal.Title>새 사이트 추가</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>사이트 명<span style={{color:"#FF0000"}}> *</span></Form.Label>
                            <Form.Control type="text" placeholder=""  name="site_name" onChange={handleInputChange} />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>사이트 상세(선택)</Form.Label>
                            <Form.Control type="text" placeholder=""  name="site_detail"/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>URL<span style={{color:"#FF0000"}}> *</span></Form.Label>
                            <Form.Control type="text" placeholder="http://"  name="site_url" onChange={handleInputChange}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <div><Form.Label>
                                대표 이미지 (500x500)<span style={{color:"#FF0000"}}> *</span>
                            </Form.Label></div>
                            {imgBase64 == "" ? null : <div><img src={imgBase64} style={{width:"50%", height : "50%"}}/></div>
                            }
                            <input type="file" id="imgFile" onChange={handleChangeFile} name="site_image" onChange={handleInputChange} />
                            {/*<Form.Control type="file" size="sm" onChange={handleChangeFile}/>*/}
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={modalClose}>
                        닫기
                    </Button>
                    <Button variant="primary" type="submit" onClick={handleSubmit}>
                        추가
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}