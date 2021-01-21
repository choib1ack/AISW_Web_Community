import React from "react";

function FAQ(props) {
    // props.data
    let data = [
        {Q : '질문1', A : '답변1'},
        {Q : '질문2', A : '답변2'}
    ];
    let items = [];
    for (let i = 0; i < Object.keys(data).length; i++) {
        items.push(
            <p key={i}>{data[i].Q}/{data[i].A}</p>
        );
    }
    return (
        <div className='FAQ'>
            {items}
        </div>
    );
}
export default FAQ;

