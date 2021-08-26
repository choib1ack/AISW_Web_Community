import React, {useEffect, useState} from "react";
import Loading from "../Loading";
import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from 'react-bootstrap/Form'
import Table from "react-bootstrap/Table";
import BannerList from "./Banner/BannerList";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Switch from "react-switch";
import axiosApi from "../../axiosApi";
import axios from "axios";

function ManageFAQ() {

    const [faqData, setFaqData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [show, setShow] = useState(false);
    const [refresh, setRefresh] = useState(0);


    const Refresh = () => {
        setRefresh(refresh + 1);
    }

    useEffect(() => {
        const fetchFaqData = async () => {
            try {
                setError(null);
                setLoading(true);

                await axios.get("/faq")
                    .then((res)=>{
                        setFaqData(res.data.data);
                    });

            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchFaqData();
    }, [refresh]);

    const handleFaqAddModalShow = () => setShow(true);


    if (loading) return <Loading/>;
    if (error) return <p> 에러가 발생했습니다{error.toString()}</p>;

    return (
        <div className='ManageFAQ'>
            <Container>
                <Title text='관리자' type='1'/>
                <Title text='자주 묻는 질문' type='2'/>

                <div id="faq_table" className="pt-3 pb-5">
                    <div id="add_fag" style={{textAlign: "right"}} className="mb-3">
                        <Button size='sm'  onClick={handleFaqAddModalShow}>
                            추가
                        </Button>

                        {show?<FaqModal
                            mode="add"
                            show={show}
                            setShow={setShow}
                            Refresh={Refresh}
                        />:null}
                    </div>


                    <Table>
                        <thead>
                        <tr>
                            <th style={{width: "80%"}}>질문/답변</th>
                            <th style={{width: "20%", textAlign: "center"}}/>
                        </tr>
                        </thead>

                        <tbody className={'english_table faq'}>

                        {faqData!=null?faqData.map(data=>(
                            <FaqManageItem
                                id={data.id}
                                question={data.question}
                                answer={data.answer}
                                setFaqData={setFaqData}
                                Refresh={Refresh}
                                />
                        )):null}
                        </tbody>
                    </Table>
                </div>
            </Container>
        </div>
    );

}export default ManageFAQ;

function FaqManageItem({question, answer, id, Refresh}){

    const [updateModalShow, setUpdateModalShow] = useState(false);

    const handleFaqModalShow = () => setUpdateModalShow(true);

    return(
        <tr key={id}>
            <td>
                <div style={{textAlign:"left", padding:"10px 20px"}}>
                    <p style={{fontWeight:"bold"}}>
                        {question}
                    </p>
                    <p>
                        {answer}
                    </p>
                </div>
            </td>
            <td style={{verticalAlign:"middle"}}>
                <Button size='sm'  onClick={handleFaqModalShow}>
                    수정
                </Button>
            </td>

            {updateModalShow?
                <FaqModal
                    mode="update"
                    id={id}
                    show={updateModalShow}
                    question={question}
                    answer={answer}
                    setShow={setUpdateModalShow}
                    Refresh={Refresh}
            />:null}

        </tr>
    );
}

function FaqModal(props){

    let mode = props.mode;

    const [faqData, setFaqData] = useState({
        question: props.question,
        answer: props.answer
    });

    const modalClose = () => {
        props.setShow(false);
    }

    const handleInputChange = (event) => {
        const target = event.target;
        const name = target.name;
        const value = target.value;

        setFaqData({
            ...faqData,
            [name]: value
        });
    }

    const handleSubmit = () => {

        if(mode==="add"){
            axiosApi.post('/auth-admin/faq',
                {data: {
                    question: faqData.question,
                    answer: faqData.answer
                }})
                .then((res) => {
                    modalClose();
                    alert("새 질문/답변이 등록되었습니다.");
                    props.Refresh();
                })
                .catch(error => {
                    console.log(error);
                    alert("새 질문/답변 등록에 실패했습니다.");
                })
        }else{
            axiosApi.put('/auth-admin/faq',
                {data: {
                        id: props.id,
                        question: faqData.question,
                        answer: faqData.answer
                    }})
                .then((res) => {
                    modalClose();
                    alert("질문/답변이 수정되었습니다.");
                    props.Refresh();
                })
                .catch(error => {
                    console.log(error);
                    alert("새 질문/답변 등록에 실패했습니다.");
                })
        }
    }

    const handleDelete = () => {
        axiosApi.delete('/auth-admin/faq/' + props.id)
            .then((res)=>{
                alert("질문/답변이 삭제 되었습니다.");
                modalClose();
                props.Refresh();
            })
            .catch(error => {
                console.log(error);
                alert("질문/답변 삭제에 실패했습니다.");
            });
    }

    return(
        <>
            <div className="FaqModal">
                <Modal show={props.show} onHide={modalClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{mode==="update"?"질문/답변 수정":"새 질문/답변"}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form style={{padding:'10px'}}>
                            <Form.Group as={Row} className="mb-3">
                                <Form.Label column sm="2">
                                    질문
                                </Form.Label>
                                <Col sm="10">
                                    <Form.Control type="text" defaultValue={faqData.question} name="question" onChange={handleInputChange}/>
                                </Col>
                            </Form.Group>

                            <Form.Group as={Row} className="mb-3" >
                                <Form.Label column sm="2">
                                    답변
                                </Form.Label>
                                <Col sm="10">
                                    <Form.Control as="textarea" rows={5}
                                                  defaultValue={faqData.answer} name="answer" onChange={handleInputChange}/>
                                </Col>
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        {mode !== "add" ?
                            <Button variant="secondary" onClick={handleDelete}>
                                삭제
                            </Button> : null}
                        <Button variant="primary" type="submit" onClick={handleSubmit}>
                            {mode === "add" ? "추가" : "수정"}
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        </>

    );
}
