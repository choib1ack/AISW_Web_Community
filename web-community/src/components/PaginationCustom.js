import Pagination from "react-bootstrap/Pagination";
import React from "react";

function PaginationCustom(props){
    let active = props.active;
    let items = [];
    for (let number = 1; number <= 5; number++) {
        items.push(
            <Pagination.Item key={number} active={number === active}>
                {number}
            </Pagination.Item>,
        );
    }
    return(
        <Pagination size="sm" className="align-self-center justify-content-center" style={{marginBottom: '3rem'}}>{items}</Pagination>
    );
}
export default PaginationCustom;
