import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import React from "react";

export default function CheckModal(props) {
    return (
        <Modal show={props.show} onHide={props.handleNo}>
            <Modal.Header closeButton>
                <Modal.Title>{props.title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>{props.body}</Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={props.handleNo}>
                    아니오
                </Button>
                <Button variant="primary" onClick={props.handleYes}>
                    확인
                </Button>
            </Modal.Footer>
        </Modal>
    );
}
