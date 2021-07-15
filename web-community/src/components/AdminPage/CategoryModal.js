import React, {useState} from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

function CategoryModal(props){

    let default_category = props.mode == "update" ? props.name : null;

    const [newCategoryName, setNewCategoryName] = useState(default_category);


    const modalClose = () => {
        props.setShowCategoryModal(false);
    }

    const handleInputChange = (event) =>{
        setNewCategoryName(event.target.value);
    }

    const handleSubmit = () =>{
        // 서버로 새 카테고리 정보 전송
        sendData(newCategoryName);
    }

    const handleDelete = () => {
        axios.delete("/site/category/"+props.id).then(res => {
            props.setShowCategoryModal(false);
            alert('카테고리가 삭제되었습니다.');
            props.setSiteData(null);
            setNewCategoryName(null);
        }).catch(err => {
            alert('카테고리 삭제에 실패했습니다.')
            console.log(err);
        })
    }

    function sendData(category_name) {
        if(props.mode=="add"){ // add
            axios.post("/site/category?name="+category_name).then(res => {
                // console.log(res);
                props.setShowCategoryModal(false);
                alert('새 카테고리가 등록되었습니다.')
                props.setSiteData(null);
                setNewCategoryName(null);
            }).catch(err => {
                alert('새 카테고리 등록에 실패했습니다.')
                console.log(err);
            })
        }else{ // update
            axios.put("/site/category/"+props.id+"?name="+category_name).then(res => {
                // console.log(res);
                props.setShowCategoryModal(false);
                alert('카테고리 정보가 수정되었습니다.')
                props.setSiteData(null);
                setNewCategoryName(null);
            }).catch(err => {
                alert('카테고리 수정에 실패했습니다.')
                console.log(err);
            })
        }

    }

    return (
        <div className="AddCategoryModal">
            <Modal show={props.showCategoryModal} onHide={modalClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{props.mode=="add"?"새 카테고리 추가":"카테고리 수정"}</Modal.Title>
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
                    {props.mode != "add" ?
                        <Button variant="secondary" onClick={handleDelete}>
                            삭제
                        </Button> : null}
                    <Button variant="primary" type="button" onClick={handleSubmit} >
                        {props.mode=="add"?"추가":"수정"}
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );


}
export default CategoryModal;