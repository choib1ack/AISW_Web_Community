import Button from "react-bootstrap/Button";
import React from "react";

function BorderButton(props) {

    let btnStyle = {
        border:'1px solid #E8E8E8',
        width:'100%',
        margin: '0.5rem',
        outline: 'none',
        boxShadow: 'none',
        backgroundColor: '#ffffff',
        color: '#B8B8B8',
        padding:'10px'
    }
    return (
        <p style={btnStyle}>{props.content}</p>
    );
}

export default BorderButton;
