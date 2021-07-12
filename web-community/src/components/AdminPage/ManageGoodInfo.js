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
    const [siteData, setSiteData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);


    useEffect(() => {
        const fetchSiteData = async () => {
            try {
                setError(null);
                setSiteData(null);
                setLoading(true);
                const response = await axios.get("/site/");
                console.log(response);
                setSiteData(Object.values(response.data.data));
            } catch (e) {
                setError(e);
            }
            setLoading(false);
        };

        fetchSiteData();
    }, []);

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

                <AddCategoryButton/>

            </Container>
        </div>
    );
}

export default ManageGoodInfo;

function AddCategoryButton(){
    const [showAddCategoryModal, setAddCategoryModal] = useState(false);

    const handleAddCategoryModalShow = () =>{
        setAddCategoryModal(true);
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
                setAddCategoryModal={setAddCategoryModal}
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
            <Title text={category_info.category} type='3'/>
            <Row>
                {category_info.site_information_api_response_list.length>0?category_info.site_information_api_response_list.map((data, index) => (
                    <SiteBox
                        key={index}
                        site_info={data}
                        setSiteData={setSiteData}
                    />
                )):null}
                <Col lg={2} md={2} sm={2}>
                    <img src={addWebPageImage} style={add_btn_style} onClick={handleAddModalShow}/>
                </Col>
            </Row>

            <SiteModal
                show={show}
                setShow={setShow}
            />
        </>
    )
}

function SiteBox({site_info,setSiteData}) {
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
                <img src={programmersImage} style={style} onClick={handleUpdateModalShow}/>
            </Col>
            <SiteModal
                show={showUpdateModal}
                setShow={setShowUpdateModal}
                info={site_info}
                setSiteData={setSiteData}
            />
        </>
    )
}

