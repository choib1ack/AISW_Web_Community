import React, {useEffect, useState} from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css"
import {ko} from "date-fns/esm/locale";
import styled from "styled-components";
import axiosApi from "../../../axiosApi";

function BannerModal(props) {
    const mode = (props.info == null) ? "add" : "update";

    const default_info = mode === "add" ?
        {
            name: "",
            start: null,
            end: null,
            url: ""
        } :
        {
            name: props.info.name,
            start: props.info.start_date.substring(0, 10),
            end: props.info.end_date.substring(0, 10),
            url: props.info.link_url
        }

    const [imgBase64, setImgBase64] = useState(""); // 파일 base64
    const [imgFile, setImgFile] = useState(null);   //파일
    const [bannerInfo, setBannerInfo] = useState(
        {
            banner_name: default_info.name,
            start_date: default_info.start,
            end_date: default_info.end,
            banner_url: default_info.url
        });   //배너

    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);

    function encodeBase64ImageTagviaFileReader(file_name) {
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
            xhr.open('GET', "/file/download/" + file_name)
            xhr.responseType = 'blob'
            xhr.send()
        })
    }

    if (mode === "update" && props.file_info != null) {
        if (imgBase64 === "") {
            encodeBase64ImageTagviaFileReader(props.file_info.file_name)
                .then(data => {
                    setImgBase64(data);
                })
        }
    }

    useEffect(() => {
        resetDate()
    }, [props.show])

    // datepicker 리셋
    const resetDate = () => {
        if (mode === "update") {
            setStartDate(new Date(default_info.start));
            setEndDate(new Date(default_info.end));
        } else {
            setStartDate(null);
            setEndDate(null);
        }
    }

    const modalClose = () => {
        setImgBase64("");
        setImgFile(null);
        setBannerInfo({
            banner_name: default_info.name,
            start_date: default_info.start,
            end_date: default_info.end,
            banner_url: default_info.url
        });
        props.setShow(false);
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
        if (name === "banner_image") {
            handleChangeFile(event);
        }
        setBannerInfo({
            ...bannerInfo,
            [name]: value
        });
    }

    const handleDelete = async () => {
        await axiosApi.delete("/auth-admin/banner/" + props.info.id)
            .then((res) => {
                alert("배너 정보가 삭제되었습니다");
                window.location.reload();
            }).catch(error => {
                let errorObject = JSON.parse(JSON.stringify(error));
                console.log(errorObject);
                alert("배너 삭제에 실패하였습니다.");
            })
    }

    const handleSubmit = (event) => {
        let formData = new FormData();
        formData.append('files', imgFile);
        formData.append('bannerApiRequest.name', bannerInfo.banner_name);
        formData.append('bannerApiRequest.startDate', bannerInfo.start_date);
        formData.append('bannerApiRequest.endDate', bannerInfo.end_date);
        formData.append('bannerApiRequest.linkUrl', bannerInfo.banner_url);

        if (mode === "update") {
            formData.append('bannerApiRequest.id', props.info.id);
        }

        if (checkNull()) {
            sendData(formData);
        }
    }

    function checkNull() {
        if (bannerInfo.banner_name === "") {
            alert("배너명을 입력해주세요.");
        } else if (bannerInfo.start_date === "") {
            alert("시작날짜를 선택해주세요.");
        } else if (bannerInfo.end_date === "") {
            alert("종료날짜를 선택해주세요.");
        } else if (imgFile == null) {
            alert("이미지를 선택해주세요.");
        } else if (bannerInfo.banner_url === "") {
            alert("배너 링크를 입력해주세요.");
        } else {
            return true;
        }
    }

    function sendData(formData) {
        if (mode === "add") {
            axiosApi.post("/auth-admin/banner", formData).then((res) => {
                alert("새 배너 등록 완료!")
                window.location.reload();
            }).catch(error => {
                console.log(error);
                alert("새 배너 등록에 실패하였습니다.");
            })
        } else {
            axiosApi.put("/auth-admin/banner", formData).then((res) => {
                alert("배너 수정 완료!")
                window.location.reload();
            }).catch(error => {
                console.log(error);
                alert("배너 수정에 실패하였습니다.");
            })
        }
    }

    // Date to String
    Date.prototype.yyyy_mm_dd = function () {
        const mm = this.getMonth() + 1; // getMonth() is zero-based
        const dd = this.getDate();

        return [this.getFullYear(),
            (mm > 9 ? '' : '0') + mm,
            (dd > 9 ? '' : '0') + dd
        ].join('-');
    };

    const handleDatePicker = (dates) => {
        let [start, end] = dates;
        setStartDate(start);
        setEndDate(end);

        if (start != null && end != null) {
            // 기간 설정
            setBannerInfo(
                {
                    ...bannerInfo,
                    start_date: start.yyyy_mm_dd(),
                    end_date: end.yyyy_mm_dd()
                }
            );
        }
    }

    return (
        <div className="AddBannerModal">
            <Modal show={props.show} onHide={modalClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{mode === "add" ? "새 배너 추가" : "배너 수정"}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label>배너 명<span style={{color: "#FF0000"}}> *</span></Form.Label>
                            <Form.Control type="text" placeholder="" defaultValue={bannerInfo.banner_name}
                                          name="banner_name" onChange={handleInputChange}/>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>게시 기간<span
                                style={{color: "#FF0000", marginRight: "20px"}}> *</span></Form.Label>
                            <SelectDate
                                selected={startDate}
                                onChange={(date) => setStartDate(date)}
                                disabled
                                placeholderText={mode === "add" ? "시작 날짜" : null}
                                dateFormat="yyyy-MM-dd"
                            />
                            ~
                            <SelectDate
                                selected={endDate}
                                onChange={(date) => setEndDate(date)}
                                disabled
                                placeholderText={mode === "add" ? "종료 날짜" : null}
                                dateFormat="yyyy-MM-dd"
                            />
                            <DatePicker
                                selected={startDate}
                                onChange={handleDatePicker}
                                startDate={startDate}
                                endDate={endDate}
                                selectsRange
                                inline
                                locale={ko}
                                dateFormat="yyyy년 MM월 dd일"
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <div><Form.Label>
                                배너 이미지 (500x500)<span style={{color: "#FF0000"}}> *</span>
                            </Form.Label></div>
                            {imgBase64 === "" ? null :
                                <div><img src={imgBase64} style={{width: "100%", objectFit: "cover"}} height="90"/>
                                </div>
                            }
                            <input type="file" id="imgFile" name="banner_image" accept='image/*'
                                   onChange={handleInputChange}/>
                            {/*<Form.Control type="file" size="sm" onChange={handleChangeFile}/>*/}
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>배너 링크<span style={{color: "#FF0000"}}> *</span></Form.Label>
                            <Form.Control type="text" placeholder="http://" defaultValue={bannerInfo.banner_url}
                                          name="banner_url" onChange={handleInputChange}/>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    {mode === "update" ?
                        <Button variant="secondary" onClick={handleDelete}>
                            삭제
                        </Button> : null}
                    <Button variant="primary" type="submit" onClick={handleSubmit}>
                        {mode === "update" ? "수정" : "추가"}
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}

export default BannerModal;

const SelectDate = styled(DatePicker)`
  width: 80px;
  height: 22px;
  padding: 0;
  font-size: 14px;
  text-align: center;
  border: 0;
  background-color: white;
  outline: none;
  cursor: pointer;
`;
