import React, {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import {Link} from "react-router-dom";

export default function FinishModal(props) {
    const [show, setShow] = useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    useEffect(() => {
        if (props.show === true) {
            handleShow();
        }
    }, [props])

    return (
        <>
            <Modal show={show} onHide={handleClose} animation={false} enforceFocus={false}>
                <Modal.Header closeButton>
                    <Modal.Title>{props.title}</Modal.Title>
                </Modal.Header>
                <Modal.Body>{props.body}</Modal.Body>
                <Modal.Footer>
                    {/*<Button variant="secondary" onClick={handleClose}>*/}
                    {/*    Close*/}
                    {/*</Button>*/}
                    <Link to={props.link}>
                        <Button variant="primary" onClick={handleClose}>
                            확인
                        </Button>
                    </Link>
                </Modal.Footer>
            </Modal>
        </>
    );
}
