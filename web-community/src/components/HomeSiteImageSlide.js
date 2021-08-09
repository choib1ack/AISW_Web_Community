import Carousel from "react-grid-carousel";
import SiteImage1 from "../siteImages/baekjoon.png";
import SiteImage2 from "../siteImages/fast_campus.png";
import SiteImage3 from "../siteImages/goorm.png";
import SiteImage4 from "../siteImages/programmers.png";
import SiteImage5 from "../siteImages/saramin.png";
import SiteImage6 from "../siteImages/jabkorea.png";
import SiteImage7 from "../siteImages/wanted.png";
import React from "react";

function HomeSiteImageSlide({siteInfo}) {
    // image url로 바꿔야함
    return(
        <>
            {/*https://github.com/x3388638/react-grid-carousel*/}
            <Carousel cols={5} rows={1} gap={20} loop>

                {siteInfo!=null ? siteInfo.map((data, index) => (
                    // data.publish_status &&
                    // (
                        <SiteItem
                            index={index}
                            src={data.file_download_url}
                            link_url={data.link_url}
                        />
                    // )
                )):null}

                {/*<Carousel.Item>*/}
                {/*    <img width="100%" src={SiteImage1} style={{border:"1px solid #E3E3E3"}}/>*/}
                {/*</Carousel.Item>*/}
                {/*<Carousel.Item>*/}
                {/*    <img width="100%" src={SiteImage2} style={{border:"1px solid #E3E3E3"}}/>*/}
                {/*</Carousel.Item>*/}
                {/*<Carousel.Item>*/}
                {/*    <img width="100%" src={SiteImage3} style={{border:"1px solid #E3E3E3"}}/>*/}
                {/*</Carousel.Item>*/}
                {/*<Carousel.Item>*/}
                {/*    <img width="100%" src={SiteImage4} style={{border:"1px solid #E3E3E3"}}/>*/}
                {/*</Carousel.Item>*/}
                {/*<Carousel.Item>*/}
                {/*    <img width="100%" src={SiteImage5} style={{border:"1px solid #E3E3E3"}}/>*/}
                {/*</Carousel.Item>*/}
                {/*<Carousel.Item>*/}
                {/*    <img width="100%" src={SiteImage6} style={{border:"1px solid #E3E3E3"}}/>*/}
                {/*</Carousel.Item>*/}
                {/*<Carousel.Item>*/}
                {/*    <img width="100%" src={SiteImage7} style={{border:"1px solid #E3E3E3"}}/>*/}
                {/*</Carousel.Item>*/}
            </Carousel>
        </>
    )
}
export default HomeSiteImageSlide;

function SiteItem({index, src, link_url}){

    const site_box_style = {
        border:"1px solid #E3E3E3"
    }

    return(
        <>
            <Carousel.Item key={index}>
                <a href={link_url}><img width="100%" src={src} style={site_box_style}/></a>
            </Carousel.Item>
        </>
    )
}