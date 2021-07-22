import React, {useState} from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Switch from "react-switch";
import Loading from "../Loading";

function SiteModal(props) {
    const mode = (!props.info) ? "add" : "update";
    // console.log(mode);

    const default_info = mode == "add" ?
        {
            name: "",
            detail: "",
            url: "",
            checked: true
        } :
        {
            name: props.info.name,
            detail: props.info.content,
            url: props.info.link_url,
            checked: props.info.publish_status
        }
    const [imgBase64, setImgBase64] = useState(""); // 파일 base64 (미리보기 전용)
    const [imgFile, setImgFile] = useState(null);	// 파일 (업로드 전용)
    const [siteInfo, setSiteInfo] = useState(
        {
            site_name: default_info.name,
            site_detail: default_info.detail,
            site_url: default_info.url,
            checked: default_info.checked,
            category: props.category_name
        });	//파일

    function encodeBase64ImageTagviaFileReader (file_name) {
        // console.log(file_name);
        return new Promise((resolve, reject) => {
            let xhr = new XMLHttpRequest()
            xhr.onload = () => {
                let reader = new FileReader()
                reader.onloadend = function () {
                    resolve(reader.result)
                }
                reader.readAsDataURL(xhr.response); // 얘가 base64로 바꿔주는애
                setImgFile(new File([xhr.response], props.file_info.file_name, {type: props.file_info.file_type}));
                // xhr.response는 실제 데이터. base64로 바꾸기 전! 이거를 업로드 해줘야함. base64로 바꾼거 올리면 엑박ㅠ
            }
            xhr.open('GET', "/file/download/"+file_name)
            xhr.responseType = 'blob'
            xhr.send()
        })
    }

    if(mode=="update" && props.file_info != null){
        if(imgBase64 == "") {
            encodeBase64ImageTagviaFileReader(props.file_info.file_name)
                .then(data => {
                    setImgBase64(data);
                })
        }
    }

    const modalClose = () => {
        setImgBase64("");
        setImgFile(null);
        props.setShow(false);
    }

    const handleDelete = async () => {
        await axios.delete("/site/" + props.info.id)
            .then((res) => {
                //console.log(res);
                props.setShow(false);
                alert("사이트 정보가 삭제되었습니다");
                props.setSiteData(null);
            }).catch(error => {
                // let errorObject = JSON.parse(JSON.stringify(error));
                // console.log(errorObject);
                alert("사이트 삭제에 실패하였습니다.");
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

    const handleInputChange = (event) => {
        const target = event.target;
        const name = target.name;
        const value = target.value;
        if (name == "site_image") {
            handleChangeFile(event);
            return;
        }
        setSiteInfo({
            ...siteInfo,
            [name]: value
        });
    }

    const handleSwitchChange = (event) => {
        setSiteInfo({
            ...siteInfo,
            checked: !siteInfo.checked
        });
    }

    const handleSubmit = (event) => {

        let site_info = {
            "content": siteInfo.site_detail,
            "category": props.category_name,
            "link_url": siteInfo.site_url,
            "name": siteInfo.site_name,
            "publish_status": siteInfo.checked
        }

        sendDataWithFile(site_info);
    }


    function sendDataWithFile(data) {

        const formData = new FormData();
        formData.append('files', imgFile);
        formData.append('siteInformationApiRequest.name', data.name);
        formData.append('siteInformationApiRequest.content', data.content);
        formData.append('siteInformationApiRequest.publishStatus', data.publish_status);
        formData.append('siteInformationApiRequest.linkUrl', data.link_url);
        formData.append('siteInformationApiRequest.category', data.category);

        if(mode=="update"){
            formData.append('siteInformationApiRequest.id', props.info.id);
        }

        // print form data log
        // console.log("FormData Log-----------");
        // for (let value of formData.values()) {
        //     console.log(value);
        // }

        if(mode=="add"){
            axios.post("/site", formData).then(res => {
                // console.log(res);
                props.setShow(false);
                alert('새 사이트가 등록되었습니다.')
                props.setSiteData(null);
            }).catch(err => {
                alert('새 사이트 등록에 실패했습니다.')
                console.log(err);
            })
        }else{
            axios.put("/site", formData).then(res => {
                // console.log(res);
                props.setShow(false);
                alert('사이트 정보가 변경되었습니다.')
                props.setSiteData(null);
            }).catch(err => {
                alert('사이트 정보 변경에 실패했습니다.')
                console.log(err);
            })
        }

    }

    return (
        <>
            <div className="AddSiteModal">
                <Modal show={props.show} onHide={modalClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{mode == "add" ? "새 사이트 추가" : "사이트 수정"}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group className="mb-3">
                                <Form.Label>사이트 명<span style={{color: "#FF0000"}}> *</span></Form.Label>
                                <Form.Control type="text" placeholder="" defaultValue={siteInfo.site_name}
                                              name="site_name" onChange={handleInputChange}/>
                            </Form.Group>
                            <Form.Group className="mb-3">
                                <Form.Label>사이트 상세(선택)</Form.Label>
                                <Form.Control type="text" placeholder="" defaultValue={siteInfo.site_detail}
                                              name="site_detail" onChange={handleInputChange}/>
                            </Form.Group>
                            <Form.Group className="mb-3">
                                <Form.Label>URL<span style={{color: "#FF0000"}}> *</span></Form.Label>
                                <Form.Control type="text" placeholder="http://" defaultValue={siteInfo.site_url}
                                              name="site_url" onChange={handleInputChange}/>
                            </Form.Group>
                            <Form.Group>
                                <Form.Label style={{width:"100%"}}>
                                    게시 여부<span style={{color: "#FF0000"}}> *</span>
                                </Form.Label>
                                <Switch
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
                            </Form.Group>

                            <Form.Group className="mb-3">
                                <div><Form.Label>
                                    대표 이미지 (500x500)<span style={{color: "#FF0000"}}> *</span>
                                </Form.Label></div>
                                {imgBase64 == "" ? <Loading/>:
                                    <div><img src={imgBase64} style={{width: "50%", height: "50%"}}/></div>
                                }
                                <input type="file" id="imgFile" name="site_image" onChange={handleInputChange}/>
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        {mode != "add" ?
                            <Button variant="secondary" onClick={handleDelete}>
                                삭제
                            </Button> : null}
                        <Button variant="primary" type="submit" onClick={handleSubmit}>
                            {mode == "add"?"추가":"수정"}
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        </>
    );
}

export default SiteModal;