import React, {useState} from "react";
import Button from "react-bootstrap/Button";

export default function YearList({selectedYear, setSelectedYear}) {
    // const [selectedYear, setSelectedYear] = useState(null);

    const year_list = [
        "2021", "2020", "2019", "2018", "2017", "2016", "2015"
    ]

    const handleClickYear = (year) =>{
        setSelectedYear(year);
    }

    let year_btn_list =[];
    for (let i = 0; i <year_list.length; i++) {
        year_btn_list.push(
            <YearButton
                key={i}
                selected_year={selectedYear}
                year={year_list[i]}
                handleClickYear={handleClickYear}
            />,
        );
    }
    return(
        <div style={{display: 'inline-block'}}>
            {year_btn_list}
        </div>
    );

}

function YearButton(props){
    let btnStyle = {
        float: 'left',
        margin: '0.5rem',
        padding: '5px 30px',
        border: props.year === props.selected_year ? '1px solid #0472FD' : '1px solid #d5d5d5',
        outline: 'none',
        boxShadow: 'none',
        radius:'0',
        backgroundColor: props.year === props.selected_year ? '#e7f1ff' :'#ffffff',
        color: props.year === props.selected_year ? '#0472FD' : '#d5d5d5'
    }
    return (
        <Button style={btnStyle} onClick={()=>props.handleClickYear(props.year)}>{props.year}</Button>
    );
}
