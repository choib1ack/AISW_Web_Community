import React, {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import Title from "../../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import BannerModal from "./BannerModal";
import Loading from "../../Loading";
import axiosApi from "../../../axiosApi";
import {CarouselList} from "../CarouselList";
import BannerList from "./BannerList";

function Bannner() {
    // const [file, setFile] = useState(false);
    // const [previewURL, setPreviewURL] = useState(storeSrc);
    const [showAddModal, setShowAddModal] = useState(false);

    const [bannerData, setBannerData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchBannerData = async () => {
            try {
                setError(null);
                setLoading(true);

                if (bannerData) {
                    setLoading(false);
                    return;
                }

                const response = await axiosApi.get("/auth-admin/banner");
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
                <Title text='관리자' type='1'/>
                <Row>
                    <Col>
                        <Title text='배너 미리 보기' type='2'/>
                    </Col>
                </Row>

                <Row style={{margin: "20px 0px"}}>
                    <CarouselList
                        banners={bannerData}
                        page="Admin"
                    />
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
                        <BannerModal show={showAddModal} setShow={setShowAddModal}/>
                        <Button style={{marginTop: '3rem', float: 'right', width: '50px'}} size='sm'
                                onClick={() => setShowAddModal(true)}>
                            등록
                        </Button>
                    </Col>
                </Row>

                <div id="banner" className="pt-3 pb-5">
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
                        <BannerList
                            banners={bannerData}
                            setBannerData={setBannerData}
                        />
                        </tbody>
                    </Table>
                </div>

            </Container>
        </div>
    );
}

export default Bannner;
