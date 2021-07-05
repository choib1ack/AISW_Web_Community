import React, {useState} from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

function SiteModal(props){

    const mode = (props.info==null)?"add":"update";
    console.log(mode);

    const default_info = mode=="add"?
        {
            name:"",
            detail:"",
            url:""
        }:
        {
            name: props.info.name,
            detail: props.info.content,
            url: props.info.link_url
        }
    const [imgBase64, setImgBase64] = useState(""); // 파일 base64
    const [imgFile, setImgFile] = useState(null);	//파일
    const [siteInfo, setSiteInfo] = useState({site_name:default_info.name, site_detail:default_info.detail, site_url:default_info.url});	//파일

    const modalClose = () => {
        setImgBase64("");
        setImgFile(null);
        props.setShow(false);
    }

    const handleDelete = async () => {
        await axios.delete("/site/"+props.info.id)
        .then((res) => {
            console.log(res);
            props.setShow(false);
            alert("사이트 정보가 삭제되었습니다") // 성공 메시지
        }).catch(error => {
            let errorObject = JSON.parse(JSON.stringify(error));
            console.log("에러 발생 (사이트 삭제)");
            console.log(errorObject);
            alert("사이트 삭제에 실패하였습니다."); // 실패 메시지
        })
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
        if(name=="site_image"){
            handleChangeFile(event);
        }
        setSiteInfo({
            ...siteInfo,
            [name]: value
        });
        console.log(siteInfo);
    }

    const handleSubmit = (event) => {
        console.log(siteInfo);
        let site_info = {
            "content": siteInfo.site_detail,
            "category": "CODINGTEST",
            "link_url": siteInfo.site_url,
            "name": siteInfo.site_name,
            "publish_status": true
        }
        console.log(site_info);
        sendData(site_info);
    }

    async function sendData(data) {
        if(mode=="add"){
            await axios.post("/site",
                {
                    headers: {
                        "Content-Type": `application/json`
                    },
                    data,
                },
            ).then((res) => {
                console.log(res);
                props.setShow(false);
                alert("새 사이트 등록완료!") // 실패 메시지
            }).catch(error => {
                let errorObject = JSON.parse(JSON.stringify(error));
                console.log("에러 발생 (새 사이트 등록)");
                console.log(errorObject);
                alert("새 사이트 등록에 실패하였습니다."); // 실패 메시지
            })
        }else{
            await axios.put("/site",
                {
                    headers: {
                        "Content-Type": `application/json`
                    },
                    data,
                },
            ).then((res) => {
                console.log(res);
                props.setShow(false);
                alert("새 사이트 등록완료!") // 실패 메시지
            }).catch(error => {
                let errorObject = JSON.parse(JSON.stringify(error));
                console.log("에러 발생 (새 사이트 등록)");
                console.log(errorObject);
                alert("새 사이트 등록에 실패하였습니다."); // 실패 메시지
            })
        }

    }

    return (
        <div className="AddSiteModal">
            <Modal show={props.show} onHide={modalClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{mode=="add"?"새 사이트 추가":"사이트 수정"}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>사이트 명<span style={{color:"#FF0000"}}> *</span></Form.Label>
                            <Form.Control type="text" placeholder="" defaultValue={siteInfo.site_name} name="site_name" onChange={handleInputChange} />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>사이트 상세(선택)</Form.Label>
                            <Form.Control type="text" placeholder="" defaultValue={siteInfo.site_detail}  name="site_detail"/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>URL<span style={{color:"#FF0000"}}> *</span></Form.Label>
                            <Form.Control type="text" placeholder="http://" defaultValue={siteInfo.site_url} name="site_url" onChange={handleInputChange}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <div><Form.Label>
                                대표 이미지 (500x500)<span style={{color:"#FF0000"}}> *</span>
                            </Form.Label></div>
                            {imgBase64 == "" ? null : <div><img src={imgBase64} style={{width:"50%", height : "50%"}}/></div>
                            }
                            <input type="file" id="imgFile" name="site_image" onChange={handleInputChange} />
                            {/*<Form.Control type="file" size="sm" onChange={handleChangeFile}/>*/}
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    {mode!="add"?
                    <Button variant="secondary" onClick={handleDelete}>
                        삭제
                    </Button>:null}
                    <Button variant="primary" type="submit" onClick={handleSubmit}>
                        추가
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}
export default SiteModal;