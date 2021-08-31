import Carousel from "react-grid-carousel";
import React from "react";
// import {BrowserView, MobileView} from 'react-device-detect';

function HomeSiteImageSlide({siteInfo}) {
    // image url로 바꿔야함

    const site_box_style = {
        border:"1px solid #E3E3E3"
    }

    console.log(siteInfo);
    return(
        <>
            {/*https://github.com/x3388638/react-grid-carousel*/}

            {/*<BrowserView>*/}
                <Carousel cols={5} rows={1} gap={20} loop mobileBreakpoint>
                    {siteInfo!=null ? siteInfo.map((data, index) => (
                        <Carousel.Item key={index}>
                            <a href={data.link_url}><img width="100%" src={data.file_download_uri[0]} style={site_box_style}/></a>
                        </Carousel.Item>

                    )):null}
                </Carousel>
            {/*</BrowserView>*/}

            {/*<MobileView>*/}
            {/*    <Carousel cols={3} rows={1} gap={20} loop>*/}
            {/*        {siteInfo!=null ? siteInfo.map((data, index) => (*/}
            {/*            <Carousel.Item key={index}>*/}
            {/*                <a href={data.link_url}><img width="100%" src={data.file_download_uri[0]} style={site_box_style}/></a>*/}
            {/*            </Carousel.Item>*/}

            {/*        )):null}*/}
            {/*    </Carousel>*/}
            {/*</MobileView>*/}

        </>
    )
}
export default HomeSiteImageSlide;
