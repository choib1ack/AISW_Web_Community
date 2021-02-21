import Button from "react-bootstrap/Button";
import React from "react";

function SelectButton(props) {
    let active;
    if (props.active === props.id) {
        active = true;
    } else {
        active = false;
    }

    let btnStyle = {
        float: 'left',
        margin: '0.5rem',
        border: '0',
        outline: 'none',
        boxShadow: 'none',
        backgroundColor: active ? '#6CBACB' : '#F4F4F4',
        color: active ? '#ffffff' : '#B8B8B8'
    }
    return (
        <Button style={btnStyle} onClick={props.onClick}>{props.title}</Button>
    );
}

export default SelectButton;
