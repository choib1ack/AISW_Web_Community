import React from "react";
import Grid from "@material-ui/core/Grid";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import './Footer.css';
import logo from "../image/logo4.png";
export default function Footer() {
    return (
        <Grid className="Footer mt-5" >
            <Row style={{backgroundColor: '#F5F5F5', padding: '15px'}} >
                <Col xs={3} className="col-align">
                    <img src={logo} style={{width:"120px"}} alt="..."/>
                </Col>
                <Col className="col-align ml-5">
                    <p className="Footer-p">
                        경기도 성남시 수정구 성남대로 1342 / 010-1234-5678 / AI_Sw@gachon.ac.kr
                    </p>
                    <p className="Footer-p">
                        @copyright 양희림, 장하영, 최준헌, 이상운, 이원창
                    </p>
                </Col>
            </Row>
        </Grid>
    );
}
