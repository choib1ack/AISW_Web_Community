import {Carousel} from "react-bootstrap";
import React from "react";

export function CarouselList({banners, page}) {
    const style = {
        width: "100%", padding: "2px", display: "flex", justifyContent: "center", alignItems: "center"
    }

    if (page === "Admin") {
        style.height = window.innerHeight * 0.2;
        style.border = "1px solid #E3E3E3";
    } else if (page === "Home") {
        style.height = window.innerHeight * 0.3;
    }

    return (
        <>
            <Carousel style={style}>
                {banners && banners.map((data, index) => (
                    (page === "Admin" && data.publish_status) ?
                        (
                            <Carousel.Item interval={1000} key={index}>
                                <a href={data.link_url}>
                                    <img className="d-block" src={data.file_api_response_list[0].file_download_uri}
                                         alt={index}
                                         height={style.height}
                                         style={{width: "100%", objectFit: "cover"}}/>
                                </a>
                            </Carousel.Item>
                        ) : page === "Home" ? (
                            <Carousel.Item interval={1000} key={index}>
                                <a href={data.link_url}>
                                    <img className="d-block" src={data.file_download_uri[0]}
                                         alt={index}
                                         height={style.height}
                                         style={{width: "100%", objectFit: "cover"}}/>
                                </a>
                            </Carousel.Item>
                        ) : null
                ))}
            </Carousel>
        </>
    )
}
