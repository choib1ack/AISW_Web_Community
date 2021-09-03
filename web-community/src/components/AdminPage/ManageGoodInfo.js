import Container from "react-bootstrap/Container";
import Title from "../Title";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import React, {useEffect, useState} from "react";
import edit_icon from "../../icon/edit_icon.png";
import addWebPageImage from "../../image/add_webpage_btn.svg";
import BorderButton from "../Button/BorderButton";
import SiteModal from "./SiteModal";
import Loading from "../Loading";
import CategoryModal from "./CategoryModal";
import axiosApi from "../../axiosApi";

function ManageGoodInfo({match}) {
    const [siteData, setSiteData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [auth, setAuth] = useState(() => window.localStorage.getItem("auth") || null);

    useEffect(() => {
        const fetchSiteData = async () => {
            try {
                setError(null);
                setLoading(true);
                if (siteData) {
                    setLoading(false);
                    return;
                }

                await axiosApi.get("/site")
                    .then((res)=>{
                        setSiteData(Object.values(res.data.data));
                        setLoading(false);
                    });
            } catch (e) {
                setError(e);
            }
        };

        fetchSiteData();
    }, [siteData]);

    if (error) return <p> 에러가 발생했습니다{error.toString()}</p>;
    if (loading) return <Loading/>;
    if (!siteData) return null;

    return (
        <div className='ManageSiteInfo'>
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

function AddCategoryButton({setSiteData}) {
    const [showCategoryModal, setShowCategoryModal] = useState(false);

    const handleAddCategoryModalShow = () => {
        setShowCategoryModal(true);
    }

    return (
        <>
            <Row style={{marginTop: '3rem'}}>
                <Col>
                    <BorderButton content='+ 새 카테고리 추가하기' onClick={handleAddCategoryModalShow}/>
                </Col>
            </Row>

            <CategoryModal
                mode="add"
                showCategoryModal={showCategoryModal}
                setShowCategoryModal={setShowCategoryModal}
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

function CategoryBox({category_info, setSiteData}) {
    const [showSiteAddModal, setShowSiteAddModal] = useState(false);
    const [showCategoryEditModal, setShowCategoryEditModal] = useState(false);

    let add_btn_style = {
        border: '1px solid #E8E8E8',
        width: '100%',
        cursor: 'pointer'
    }

    let style = {
        fontSize: '14px',
        textAlign: 'left',
        marginTop: '3rem',
        fontWeight: 'bold',
        color: '#1c1c1c'
    }

    const handleSiteAddModalShow = () => setShowSiteAddModal(true);
    const handleCategoryEditModalShow = () => setShowCategoryEditModal(true);

    if (!category_info) return null;

    return (
        <>
            <p style={style}>{category_info.name}
                <img src={edit_icon} height="12px" style={{paddingLeft: '10px', cursor: 'pointer'}}
                     onClick={handleCategoryEditModalShow}/>
            </p>

            <Row>
                {category_info.site_information_api_response_list.length > 0 ? category_info.site_information_api_response_list.map((data, index) => (
                    <SiteBox
                        key={index}
                        site_info={data}
                        setSiteData={setSiteData}
                        category_name={category_info.name}
                    />
                )) : null}
                <Col lg={2} md={2} sm={2}>
                    <img src={addWebPageImage} style={add_btn_style} onClick={handleSiteAddModalShow}/>
                </Col>
            </Row>

            {showSiteAddModal ? <SiteModal
                show={showSiteAddModal}
                setShow={setShowSiteAddModal}
                setSiteData={setSiteData}
                category_name={category_info.name}
            /> : null}

            {showCategoryEditModal ? <CategoryModal
                mode="update"
                id={category_info.id}
                name={category_info.name}
                showCategoryModal={showCategoryEditModal}
                setShowCategoryModal={setShowCategoryEditModal}
                setSiteData={setSiteData} // 새로고침용
            /> : null}

        </>
    )
}

function SiteBox({site_info, setSiteData, category_name}) {
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
                <img src={site_info.file_api_response_list[0].file_download_uri} style={style}
                     onClick={handleUpdateModalShow}/>
            </Col>
            {showUpdateModal ? <SiteModal
                show={showUpdateModal}
                setShow={setShowUpdateModal}
                info={site_info}
                setSiteData={setSiteData}
                category_name={category_name}
                file_info={site_info.file_api_response_list[0]}
            /> : null}
        </>
    )
}
