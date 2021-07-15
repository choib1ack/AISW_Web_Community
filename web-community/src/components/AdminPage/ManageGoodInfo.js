import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import React, {useEffect, useState} from "react";
import programmersImage from "../../siteImages/programmers.png";
import addWebPageImage from "../../image/add_webpage_btn.svg";
import BorderButton from "../Button/BorderButton";
import axios from "axios";
import SiteModal from "./SiteModal";
import Loading from "../Loading";
import AddCategoryModal from "./AddCategoryModal";

function ManageGoodInfo({match}) {
    const [siteData, setSiteData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);


    useEffect(() => {
        const fetchSiteData = async () => {
            try {
                setError(null);
                //setSiteData(null);
                setLoading(true);
                console.log(siteData);
                if(siteData) {
                    setLoading(false);
                    return;
                }

                const response = await axios.get("/site/");
                console.log(response);
                setSiteData(Object.values(response.data.data));
                setLoading(false);
            } catch (e) {
                setError(e);
            }

        };

        fetchSiteData();
    }, [siteData]);

    if (loading) return <Loading/>;
    if (error) return <p> 에러가 발생했습니다{error.toString()}</p>;
    if (!siteData) return null;

    return (
        <div className='Manager'>
            <Container>
                <Title text='관리자' type='1'/>
                <Title text='유용한 사이트' type='2'/>

                <MakeSiteList
                    categories={siteData}
                    setSiteData={setSiteData}
                />

                <AddCategoryButton
                    setSiteData={setSiteData}
                />

            </Container>
        </div>
    );
}

export default ManageGoodInfo;

function AddCategoryButton({setSiteData}){
    const [showAddCategoryModal, setShowAddCategoryModal] = useState(false);

    const handleAddCategoryModalShow = () =>{
        setShowAddCategoryModal(true);
    }

    return(
        <>
            <Row style={{marginTop: '3rem'}} >
                <Col>
                    <BorderButton content='+ 새 카테고리 추가하기' onClick={handleAddCategoryModalShow}/>
                </Col>
            </Row>

            <AddCategoryModal
                showAddCategoryModal={showAddCategoryModal}
                setShowAddCategoryModal={setShowAddCategoryModal}
                setSiteData={setSiteData}
            />
        </>
    )
}

function MakeSiteList({categories, setSiteData}) {

    return (
        <>
            {categories.map((data, index) => (
                <CategoryBox
                    key={index}
                    category_info={data}
                    setSiteData={setSiteData}
                />
            ))}

        </>
    )
}

function CategoryBox({category_info,setSiteData}) {
    const [show, setShow] = useState(false);

    let add_btn_style = {
        border: '1px solid #E8E8E8',
        width: '100%',
        cursor: 'pointer'
    }

    const handleAddModalShow = () => setShow(true);
    // console.log("카테고리-");
    // console.log(category_info);
    if (!category_info) return null;

    return (
        <>
            <Title text={category_info.name} type='3'/>
            <Row>
                {category_info.site_information_api_response_list.length>0?category_info.site_information_api_response_list.map((data, index) => (
                    <SiteBox
                        key={index}
                        site_info={data}
                        setSiteData={setSiteData}
                        category_name={category_info.name}
                    />
                )):null}
                <Col lg={2} md={2} sm={2}>
                    <img src={addWebPageImage} style={add_btn_style} onClick={handleAddModalShow}/>
                </Col>
            </Row>

            <SiteModal
                show={show}
                setShow={setShow}
                setSiteData={setSiteData}
                category_name={category_info.name}
            />
        </>
    )
}

function SiteBox({site_info,setSiteData, category_name}) {
    const [showUpdateModal, setShowUpdateModal] = useState(false);

    let style = {
        border: '1px solid #C0C0C0',
        width: '100%',
        cursor: 'pointer'
    }

    const handleUpdateModalShow = () => {
        setShowUpdateModal(true);
    };

    return (
        <>
            <Col lg={2} md={2} sm={2} id={site_info.id}>
                <img src={site_info.file_api_response_list[0].file_download_uri} style={style} onClick={handleUpdateModalShow} />
            </Col>
            <SiteModal
                show={showUpdateModal}
                setShow={setShowUpdateModal}
                info={site_info}
                setSiteData={setSiteData}
                category_name={category_name}
            />
        </>
    )
}
