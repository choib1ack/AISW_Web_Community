import React, {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import MakeBoardList from "../Board/MakeBoardList";
import Table from "react-bootstrap/Table";
import Switch from "react-switch";
import {useDispatch, useSelector} from "react-redux";
import {setBanner} from "../../features/bannerSlice";
import FinishModal from "../FinishModal";
import AddBannerModal from "./AddBannerModal";
import exampleBanner from "../../image/banner_example1.svg"
import {Carousel} from "react-bootstrap";

function Bannner() {
    // redux toolkit
    const storeSrc = useSelector(state => state.banner.src);
    const dispatch = useDispatch();

    const [state, setState] = useState({checked: false});
    const [file, setFile] = useState(false);
    const [previewURL, setPreviewURL] = useState(storeSrc);
    const [modalShow, setModalShow] = useState(false);
    const [newModalShow, setNewModalShow] = useState(false);

    const handleChange = (checked) => {
        setState({checked});
    };

    const handleFileOnChange = (event) => {
        event.preventDefault();
        let reader = new FileReader();
        let file = event.target.files[0];
        reader.onloadend = () => {
            setFile(file)
            setPreviewURL(reader.result)
        }
        reader.readAsDataURL(file);
    }

    const handleFileOnSubmit = () => {
        // redux store에 저장
        dispatch(setBanner(previewURL));
        setModalShow(true);
    }

    return (
        <div>
            <Container>
                <FinishModal show={modalShow} link={`/`}
                             title="배너" body="배너 변경이 완료되었습니다 !"/>

                <Title text='관리자' type='1'/>
                <Row>
                    <Col>
                        <Title text='배너 관리' type='2'/>
                    </Col>
                    {/*<Col>*/}
                    {/*    <form>*/}
                    {/*        <div className="form-group mb-0" style={{float: 'right'}}>*/}
                    {/*            <Row>*/}
                    {/*                <Col>*/}
                    {/*                    <label className="btn-secondary rounded px-3 " htmlFor="*/}
                    {/*            input-file" style={{*/}
                    {/*                        fontSize: ".875rem",*/}
                    {/*                        cursor: "pointer",*/}
                    {/*                        width: '50px',*/}
                    {/*                        height: '28px',*/}
                    {/*                        display: 'flex',*/}
                    {/*                        alignItems: 'center',*/}
                    {/*                        marginTop: '3rem'*/}
                    {/*                    }}*/}
                    {/*                    >*/}
                    {/*                        변경*/}
                    {/*                    </label>*/}
                    {/*                    <input type="file" id="input-file" style={{display: "none"}}*/}
                    {/*                           accept='image/*'*/}
                    {/*                           onChange={handleFileOnChange}/>*/}
                    {/*                </Col>*/}
                    {/*                <Col>*/}
                    {/*                    <Button style={{*/}
                    {/*                        width: '50px', marginTop: '3rem'*/}
                    {/*                    }} size='sm' onClick={handleFileOnSubmit}>*/}
                    {/*                        확인*/}
                    {/*                    </Button>*/}
                    {/*                </Col>*/}
                    {/*            </Row>*/}
                    {/*        </div>*/}
                    {/*    </form>*/}
                    {/*</Col>*/}
                </Row>

                <Row style={{margin: "20px 0px"}}>
                    <Carousel style={{
                        border: "1px solid #E3E3E3", width: "100%", height: "160px",
                        padding: "2px", display: "flex", justifyContent: "center", alignItems: "center"
                    }}>
                        <Carousel.Item interval={1000}>
                            <img className="d-block" src={storeSrc} alt="First slide"
                                 height={150}
                                 style={{width: "100%", objectFit: "cover"}}/>
                        </Carousel.Item>
                        <Carousel.Item interval={1000}>
                            <img className="d-block" src={exampleBanner} alt="Second slide"
                                 height={150}
                                 style={{width: "100%", objectFit: "cover"}}/>
                        </Carousel.Item>
                    </Carousel>

                    {/*<Col className="p-3">*/}
                    {/*    <div style={{float: 'right', color: "#636363"}}>*/}
                    {/*        <p className="d-inline-block mr-2">*/}
                    {/*            최종 수정일:*/}
                    {/*        </p>*/}
                    {/*        <p className="d-inline-block">*/}
                    {/*            2016-10-31 23:59:59*/}
                    {/*        </p>*/}
                    {/*    </div>*/}
                    {/*</Col>*/}
                </Row>

                <Row>
                    <Col>
                        <p style={{
                            fontSize: '14px',
                            textAlign: 'left',
                            marginTop: '4rem',
                            fontWeight: 'bold'
                        }}>
                            배너 변경 내역
                        </p>
                    </Col>
                    <Col>
                        <AddBannerModal show={newModalShow} setShow={setNewModalShow}/>
                        <Button style={{marginTop: '3rem', float: 'right', width: '50px'}} size='sm'
                                onClick={() => setNewModalShow(true)}>
                            등록
                        </Button>
                    </Col>
                </Row>

                <div id="banner" className="pt-3">
                    <Table>
                        <thead>
                        <tr>
                            <th style={{width: "20%"}}>등록 날짜</th>
                            <th style={{width: "20%"}}>배너명</th>
                            <th style={{width: "40%"}}>게시 기간</th>
                            <th style={{width: "10%"}}>게시 여부</th>
                            <th style={{width: "20%"}}></th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td className="middle">
                                2021-05-21
                            </td>
                            <td className="middle">
                                중간고사 간식행사
                            </td>
                            <td className="middle">
                                2021-05-22 ~ 2021-06-05
                            </td>
                            <td className="middle">
                                <Switch
                                    checked={state.checked}
                                    onChange={handleChange}
                                    onColor="#E7F1FF"
                                    onHandleColor="#0472fd"
                                    handleDiameter={23}
                                    uncheckedIcon={false}
                                    checkedIcon={false}
                                    boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
                                    activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
                                    height={20}
                                    width={40}
                                    className="react-switch"
                                    id="material-switch"
                                />
                            </td>
                            <td className="middle">
                                <Button size='sm'>
                                    수정
                                </Button>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                </div>

            </Container>
        </div>
    )
        ;
}

export default Bannner;
