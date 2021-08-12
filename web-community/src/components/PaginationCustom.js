import Pagination from "react-bootstrap/Pagination";
import React from "react";

function PaginationCustom(props){
    let active = props.pageInfo.current_page+1;
    let items = [];

    const handlePageClick = (number) => {
        props.setPagination(number);
    }

    for (let number = 1; number <= props.pageInfo.total_pages; number++) {
        items.push(
            <Pagination.Item
                key={number}
                active={number === active}
                onClick={()=>handlePageClick(number-1)}
            >
                {number}
            </Pagination.Item>,
        );
    }
    return(
        <Pagination size="sm" className="align-self-center justify-content-center" style={{marginBottom: '3rem'}}>{items}</Pagination>
    );
}

export default PaginationCustom;
