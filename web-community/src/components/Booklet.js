import React, {useState} from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Title from "./Title";
import YearList from "./YearList";
import PDF2020 from "./booklet/2020_sw.pdf";
import PDF2019 from "./booklet/2019_sw.pdf";
import PDF2018 from "./booklet/2018_sw.pdf";
import PDF2017 from "./booklet/2017_sw.pdf";
import PDF2016 from "./booklet/2016_sw.pdf";
import PDF2015 from "./booklet/2015_sw.pdf";
import { Worker } from '@react-pdf-viewer/core';
import { Viewer, SpecialZoomLevel, } from '@react-pdf-viewer/core';
// Plugins
import { defaultLayoutPlugin } from '@react-pdf-viewer/default-layout';
// Import styles
import '@react-pdf-viewer/core/lib/styles/index.css';
import '@react-pdf-viewer/default-layout/lib/styles/index.css';

export default function Booklet() {
    const [selectedYear, setSelectedYear] = useState("2020");

    //https://github.com/react-pdf-viewer/examples

    const year_list = [
        "2020", "2019", "2018", "2017", "2016", "2015"
        // "2021", "2020", "2019", "2018", "2017", "2016", "2015"
    ]

    return (
        <div className="Booklet">
            <Container className="mb-5">
                <Row style={{marginBottom: '1rem'}}>
                    <Col>
                        <Title text='학번별 학사요람' type='1'/>
                    </Col>
                </Row>
                <YearList
                    yearList={year_list}
                    selectedYear={selectedYear}
                    setSelectedYear={setSelectedYear}
                />

                <Worker workerUrl="https://unpkg.com/pdfjs-dist@2.6.347/build/pdf.worker.min.js"/>

                <PDFView
                    selectedYear={selectedYear}/>
                <Row style={{marginBottom: '3rem'}}/>
            </Container>
        </div>
    );
}

function PDFView(props){
    let file;
    switch (props.selectedYear) {
        case "2020": file = PDF2020; break;
        case "2019": file = PDF2019; break;
        case "2018": file = PDF2018; break;
        case "2017": file = PDF2017; break;
        case "2016": file = PDF2016; break;
        case "2015": file = PDF2015; break;
    }
    const defaultLayoutPluginInstance = defaultLayoutPlugin();
    return (
        <div
            style={{
                border: '1px solid rgba(0, 0, 0, 0.3)',
                height: '750px',
            }}
        >
            {/*<Viewer fileUrl={file}*/}
            {/*        defaultScale={SpecialZoomLevel.PageFit}*/}
            {/*/>*/}
            <Viewer
                fileUrl={file}
                plugins={[
                    // Register plugins
                    defaultLayoutPluginInstance
                ]}
            />
        </div>
    );

}
