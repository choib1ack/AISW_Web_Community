import React, {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import Switch from "react-switch";
import {useDispatch, useSelector} from "react-redux";
import {setBanner} from "../../features/bannerSlice";
import FinishModal from "../FinishModal";
import AddBannerModal from "./AddBannerModal";
import exampleBanner from "../../image/banner_example1.svg"
import {Carousel} from "react-bootstrap";
import axios from "axios";
import Loading from "../Loading";

function Bannner() {
    // redux toolkit
    const storeSrc = useSelector(state => state.banner.src);
    const dispatch = useDispatch();

    // const [file, setFile] = useState(false);
    // const [previewURL, setPreviewURL] = useState(storeSrc);
    const [modalShow, setModalShow] = useState(false);
    const [newModalShow, setNewModalShow] = useState(false);

    const [bannerData, setBannerData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    // const handleFileOnChange = (event) => {
    //     event.preventDefault();
    //     let reader = new FileReader();
    //     let file = event.target.files[0];
    //     reader.onloadend = () => {
    //         setFile(file)
    //         setPreviewURL(reader.result)
    //     }
    //     reader.readAsDataURL(file);
    // }
    //
    // const handleFileOnSubmit = () => {
    //     // redux store에 저장
    //     dispatch(setBanner(previewURL));
    //     setModalShow(true);
    // }

    useEffect(() => {
        const fetchBannerData = async () => {
            try {
                setError(null);
                setLoading(true);
                console.log(bannerData[0].file_api_response_list[0].file_download_uri);

                if (bannerData.length !== 0) {
                    setLoading(false);
                    return;
                }

                const response = await axios.get("/banner/");
                console.log(response);
                setBannerData(Object.values(response.data.data));
                setLoading(false);
            } catch (e) {
                setError(e);
            }
        };
        fetchBannerData();
    }, [bannerData]);

    if (loading) return <Loading/>;
    if (error) return <p> 에러가 발생했습니다{error.toString()}</p>;
    if (!bannerData) return null;

    return (
        <div>
            <Container>
                <FinishModal show={modalShow} link={`/`}
                             title="배너" body="배너 변경이 완료되었습니다 !"/>

                <Title text='관리자' type='1'/>
                <Row>
                    <Col>
                        <Title text='배너 관리' type='2'/>
                    </Col>
                </Row>

                <Row style={{margin: "20px 0px"}}>
                    <Carousel style={{
                        border: "1px solid #E3E3E3", width: "100%", height: "160px",
                        padding: "2px", display: "flex", justifyContent: "center", alignItems: "center"
                    }}>
                        <Carousel.Item interval={1000}>
                            <img className="d-block" src={bannerData[0].file_api_response_list[0].file_download_uri} alt="First slide"
                                 height={150}
                                 style={{width: "100%", objectFit: "cover"}}/>
                        </Carousel.Item>
                        <Carousel.Item interval={1000}>
                            <img className="d-block" src={bannerData[1].file_api_response_list[0].file_download_uri} alt="Second slide"
                                 height={150}
                                 style={{width: "100%", objectFit: "cover"}}/>
                        </Carousel.Item>
                    </Carousel>
                </Row>

                <Row>
                    <Col>
                        <p style={{
                            fontSize: '14px',
                            textAlign: 'left',
                            marginTop: '4rem',
                            fontWeight: 'bold'
                        }}>
                            배너 변경 내역
                        </p>
                    </Col>
                    <Col>
                        <AddBannerModal show={newModalShow} setShow={setNewModalShow}/>
                        <Button style={{marginTop: '3rem', float: 'right', width: '50px'}} size='sm'
                                onClick={() => setNewModalShow(true)}>
                            등록
                        </Button>
                    </Col>
                </Row>

                <div id="banner" className="pt-3">
                    <Table>
                        <thead>
                        <tr>
                            <th style={{width: "20%"}}>등록 날짜</th>
                            <th style={{width: "20%"}}>배너명</th>
                            <th style={{width: "40%"}}>게시 기간</th>
                            <th style={{width: "10%"}}>게시 여부</th>
                            <th style={{width: "20%"}}></th>
                        </tr>
                        </thead>

                        <tbody>
                        <MakeBannerList
                            banners={bannerData}
                            setBannerData={setBannerData}
                        />
                        </tbody>
                    </Table>
                </div>

            </Container>
        </div>
    )
        ;
}

export default Bannner;

function MakeBannerList({banners, setBannerData}) {
    return (
        <>
            {banners.map((data, index) => (
                <BannerBox
                    key={index}
                    banner_info={data}
                    setBannerData={setBannerData}
                />
            ))}
        </>
    )
}

function BannerBox({banner_info, setBannerData}) {
    return (
        <tr>
            <td className="middle">
                {banner_info.created_at.substr(0, 10)}
            </td>
            <td className="middle">
                {banner_info.name}
            </td>
            <td className="middle">
                {banner_info.start_date.substr(0, 10)} ~ {banner_info.end_date.substr(0, 10)}
            </td>
            <td className="middle">
                <Switch
                    checked={banner_info.publish_status}
                    onChange={() => false}
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
            </td>
            <td className="middle">
                <Button size='sm'>
                    수정
                </Button>
            </td>
        </tr>
    )
}
