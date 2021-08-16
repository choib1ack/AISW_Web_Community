import React, {useState} from "react";
import Switch from "react-switch";
import BannerModal from "./BannerModal";
import Button from "react-bootstrap/Button";

export default function BannerItem({banner_info, setBannerData}) {
    const [showUpdateModal, setShowUpdateModal] = useState(false);

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
                {showUpdateModal ? <BannerModal show={showUpdateModal} setShow={setShowUpdateModal}
                                                info={banner_info} setBannerData={setBannerData} mode="update"
                                                file_info={banner_info.file_api_response_list[0]}
                /> : null}

                <Button size='sm' onClick={() => setShowUpdateModal(true)}>
                    수정
                </Button>
            </td>
        </tr>
    )
}
