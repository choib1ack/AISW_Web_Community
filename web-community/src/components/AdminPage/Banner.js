import React, {useState} from "react";
import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import MakeBoardList from "../Board/MakeBoardList";
import Table from "react-bootstrap/Table";
import Switch from "react-switch";

function Bannner() {
    const [state, setState] = useState({checked: false});
    const [file, setFile] = useState(false);
    const [previewURL, setPreviewURL] = useState('');

    const handleChange = (checked) => {
        setState({checked});
        console.log(state);
        console.log("event")
    };

    const handleFileOnChange = (event) => {

    }

    return (
        <div>
            <Container>
                <Title text='관리자' type='1'/>
                <Row>
                    <Col>
                        <Title text='배너 관리' type='2'/>
                    </Col>
                    <Col>
                        <form>
                            <div className="form-group">
                                <Button variant="secondary" size='sm'
                                        style={{marginTop: '3rem', float: 'right', width: '50px', height: '30px'}}>
                                    <label htmlFor="exampleFormControlFile1"
                                           style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                                        변경</label>
                                    <input type="file" className="form-control-file" id="exampleFormControlFile1"
                                           onChange={handleFileOnChange}
                                           hidden/>
                                </Button>
                            </div>
                        </form>
                    </Col>
                </Row>

                <Row style={{margin: "20px 0px"}}>

                    <div style={{
                        border: "1px solid #E3E3E3", width: "100%", height: "100px",
                        display: "flex", justifyContent: "center", alignItems: "center"
                    }}>
                        {
                            file === false ? (
                                <p style={{color: "#636363"}}>미리보기</p>
                            ) : (
                                <img className='profile_preview' src={previewURL} alt='미리보기'/>
                            )
                        }
                    </div>
                    <Col className="p-3">
                        <div style={{float: 'right', color: "#636363"}}>
                            <p className="d-inline-block mr-2">
                                최종 수정일:
                            </p>
                            <p className="d-inline-block">
                                2016-10-31 23:59:59
                            </p>
                        </div>
                    </Col>
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
                        <Button style={{marginTop: '3rem', float: 'right', width: '50px'}} size='sm'>
                            등록
                        </Button>
                    </Col>
                </Row>

                <div id="banner" className="pt-3">
                    <Table>
                        <thead>
                        <tr>
                            <th style={{width: "20%"}}>게시 날짜</th>
                            <th style={{width: "20%"}}>배너명</th>
                            <th style={{width: "40%"}}>게시 기간</th>
                            <th style={{width: "10%"}}>게시 여부</th>
                            <th style={{width: "20%"}}></th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td class="middle">
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
    );
}

export default Bannner;
