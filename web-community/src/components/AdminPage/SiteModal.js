import React, {useState} from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Switch from "react-switch";

function SiteModal(props){

    const mode = (props.info==null)?"add":"update";
    console.log(mode);

    const default_info = mode=="add"?
        {
            name:"",
            detail:"",
            url:"",
            checked : true
        }:
        {
            name: props.info.name,
            detail: props.info.content,
            url: props.info.link_url,
            checked : props.info.publish_status //맞나?
        }
    const [imgBase64, setImgBase64] = useState(""); // 파일 base64
    const [imgFile, setImgFile] = useState(null);	//파일
    const [siteInfo, setSiteInfo] = useState({site_name:default_info.name, site_detail:default_info.detail, site_url:default_info.url, checked:default_info.checked});	//파일

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
            props.setSiteData([]);
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
        const name = target.name;
        const value = target.value;
        if(name=="site_image"){
            handleChangeFile(event);
            return;
        }
        setSiteInfo({
            ...siteInfo,
            [name]: value
        });
        console.log(siteInfo);
    }

    const handleSwitchChange = (event) =>{
        setSiteInfo({
            ...siteInfo,
            checked: !siteInfo.checked
        });
    }

    const handleSubmit = (event) => {
        console.log(siteInfo);
        let site_info = {
            "content": siteInfo.site_detail,
            "category": "CODINGTEST",
            "link_url": siteInfo.site_url,
            "name": siteInfo.site_name,
            "publish_status": siteInfo.checked
        }
        console.log("입력된 정보---------");
        console.log(site_info);
        sendDataWithFile(site_info);
    }

    // const handleSubmit = (event) => {
    //     const formData = new FormData();
    //     formData.append('file', imgFile);
    //     formData.append('content', siteInfo.site_detail);
    //     formData.append('category', "CODINGTEST");
    //     formData.append('link_url', siteInfo.site_url);
    //     formData.append('name', siteInfo.site_name);
    //     formData.append('publish_status', siteInfo.checked);
    //
    //     console.log(formData);
    //     sendDataWithFile(formData);
    // }

    function sendDataWithFile(data) {

        console.log("입력된 정보2---------");
        console.log(data);

        const formData = new FormData();
        formData.append('files', imgFile);

        formData.append('siteInformationApiRequest.name', data.name);
        formData.append('siteInformationApiRequest.content', data.content);
        formData.append('siteInformationApiRequest.publishStatus', data.publish_status);
        formData.append('siteInformationApiRequest.linkUrl', data.link_url);
        formData.append('siteInformationApiRequest.category', data.category);

        // print log
        console.log("FormData Log-----------");
        for (let value of formData.values()) {
            console.log(value);
        }

        axios.post("/site", formData).then(res => {
            console.log(res);
            alert('성공')
        }).catch(err => {
            alert('실패')
        })
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
                console.log(res.data.data);
                sendDataWithFile(res.data.data.id, data);
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
                            <Form.Control type="text" placeholder="" defaultValue={siteInfo.site_detail}  name="site_detail" onChange={handleInputChange}/>
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
                        <Form.Group>
                            <Form.Label>
                                <Switch
                                    // 이거 변수명 맞나?
                                    checked={siteInfo.checked}
                                    onChange={handleSwitchChange}
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
                            </Form.Label>
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