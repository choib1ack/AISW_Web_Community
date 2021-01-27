import React from "react";
import './Notice.css';
import NoticeList from "./NoticeList";
import {Route} from "react-router-dom";
import NoticeDetail from "./NoticeDetail";

function Notice({match}) {
    return (
        <>
            <Route exact path={match.path} component={NoticeList}/>
            <Route path={`${match.path}/:id`} component={NoticeDetail}/>
        </>
    );
}

export default Notice;

