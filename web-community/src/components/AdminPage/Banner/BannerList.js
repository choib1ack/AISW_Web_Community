import React from "react";
import BannerItem from "./BannerItem";

export default function BannerList({banners, setBannerData}) {
    return (
        <>
            {banners.map((data, index) => (
                <BannerItem
                    key={index}
                    banner_info={data}
                    setBannerData={setBannerData}
                />
            ))}
        </>
    )
}
