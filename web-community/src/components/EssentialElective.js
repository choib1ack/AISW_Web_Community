import React, {useState} from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Title from "./Title";
import image_14_15 from "../image/교필_14_15.png"
import image_16_17 from "../image/교필_16_17.png"
import image_18_19 from "../image/교필_18_19.png"
import YearList from "./YearList";

export default function EssentialElective() {
    const [selectedYear, setSelectedYear] = useState("2018~2019");

    const year_list = ["2018~2019", "2016~2017", "2014~2015"];

    return (
        <div className="Booklet">
            <Container className="mb-5">
                <Row style={{marginBottom: '1rem'}}>
                    <Col>
                        <Title text='학번별 필수교양' type='1'/>
                    </Col>
                </Row>
                <YearList
                    yearList={year_list}
                    selectedYear={selectedYear}
                    setSelectedYear={setSelectedYear}
                />
                <ElectiveImageView
                    selectedYear={selectedYear}/>

                <Row style={{marginBottom: '3rem'}}/>
            </Container>
        </div>
    );
}

function ElectiveImageView({selectedYear}){
    let image;
    switch (selectedYear) {
        case "2014~2015": image = image_14_15; break;
        case "2016~2017": image = image_16_17; break;
        case "2018~2019": image = image_18_19; break;
    }
    return (
        <div>
            <img src={image} style={{height:"100%", weight:"auto"}}/>
        </div>
    );

}