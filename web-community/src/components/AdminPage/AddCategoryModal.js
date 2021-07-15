import React, {useState} from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

function AddCategoryModal(props){

    const [newCategoryName, setNewCategoryName] = useState(null);


    const modalClose = () => {
        props.setShowAddCategoryModal(false);
    }

    const handleInputChange = (event) =>{
        setNewCategoryName(event.target.value);
        console.log(newCategoryName);
    }

    const handleSubmit = () =>{
        // 서버로 새 카테고리 정보 전송
        sendData(newCategoryName);
    }

    function sendData(new_category_name) {
        axios.post("/site/category?name="+new_category_name).then(res => {
            // console.log(res);
            props.setShowAddCategoryModal(false);
            alert('새 카테고리가 등록되었습니다.')
            props.setSiteData(null);
            setNewCategoryName(null);
        }).catch(err => {
            alert('새 카테고리 등록에 실패했습니다.')
            console.log(err);
        })
    }

    return (
        <div className="AddCategoryModal">
            <Modal show={props.showAddCategoryModal} onHide={modalClose}>
                <Modal.Header closeButton>
                    <Modal.Title>새 카테고리 추가</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>새 카테고리 명<span style={{color:"#FF0000"}}> *</span></Form.Label>
                            <Form.Control type="text" placeholder="" defaultValue={newCategoryName} name="category_name"
                                          onChange={handleInputChange}/>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" type="button" onClick={handleSubmit} >
                        추가
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );


}
export default AddCategoryModal;