import React, {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import {Link} from "react-router-dom";

export default function FinishModal(props) {
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    return (
        <>
            <Modal show={show} onHide={handleClose} animation={false}>
                <Modal.Header closeButton>
                    <Modal.Title>회원가입</Modal.Title>
                </Modal.Header>
                <Modal.Body>회원가입이 완료되었습니다 !</Modal.Body>
                <Modal.Footer>
                    {/*<Button variant="secondary" onClick={handleClose}>*/}
                    {/*    Close*/}
                    {/*</Button>*/}
                    <Link to={`/login`}>
                        <Button variant="primary" onClick={handleClose}>
                            확인
                        </Button>
                    </Link>
                </Modal.Footer>
            </Modal>
        </>
    );
}
