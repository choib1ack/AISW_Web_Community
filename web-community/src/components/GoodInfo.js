import React, {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Title from "./Title";
import Card from "react-bootstrap/Card";
import axios from "axios";
import Loading from "./Loading";

function GoodInfo() {
    const [siteData, setSiteData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    window.scrollTo(0, 0);

    useEffect(() => {
        const fetchSiteData = async () => {
            try {
                setError(null);
                setLoading(true);
                if (siteData) {
                    setLoading(false);
                    return;
                }
                const response = await axios.get("/site/");
                setSiteData(Object.values(response.data.data));
                setLoading(false);
            } catch (e) {
                setError(e);
            }
        };

        fetchSiteData();
    }, []);

    if (loading) return <Loading/>;
    if (error) return <p> 에러가 발생했습니다{error.toString()}</p>;
    if (!siteData) return null;

    return (
        <div className='ContestInfo'>
            <Container>
                <Title text='유용한 사이트' type='1'/>
                <MakeSiteList
                    categories={siteData}
                />
            </Container>
        </div>
    );
}

export default GoodInfo;

function MakeSiteList({categories}) {

    return (
        <>
            {categories.map((data, index) => (
                <CategoryBox
                    key={index}
                    category_info={data}
                />
            ))}

        </>
    )
}

function CategoryBox({category_info}) {

    if (!category_info) return null;
    if (category_info.site_information_api_response_list.length == 0) return null;

    return (
        <>
            <Title text={category_info.name} type='2'/>
            <Row style={{marginBottom: '3rem'}}>
                {category_info.site_information_api_response_list
                    .map((data, index) => (
                        data.publish_status?
                        <Col lg={3} md={4} sm={6}>
                            <SiteBox
                                key={index}
                                site_info={data}
                                category_name={category_info.name}
                            />
                        </Col>:null
                    ))}
            </Row>
        </>
    )
}

function SiteBox({site_info}) {
    console.log(site_info);
    return (
        <Card style={{width: '100%', marginBottom: '1rem'}} className="text-center">
            <a href={site_info.link_url} target='_blank'>
                <img src={site_info.file_api_response_list[0].file_download_uri}
                     style={{width: '100%', height: '50%'}}/>
            </a>
            <Card.Body>
                <Card.Title style={{fontSize: '15px', textAlign: "center"}}>{site_info.name}</Card.Title>
                <Card.Subtitle style={{fontSize: '12px', color: '#B8B8B8'}}>{site_info.content}</Card.Subtitle>
            </Card.Body>
        </Card>

    )
}
