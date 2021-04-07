import React from "react";
import './Notice.css';
import NoticeList from "./NoticeList";
import {Route} from "react-router-dom";
import NoticeDetail from "./NoticeDetail";
import NewNotice from "./NewNotice";
import EditNotice from "./EditNotice";

function Notice({match}) {
    return (
        <>
            <Route exact path={match.path} component={NoticeList}/>
            <Route path={`${match.path}/newNotice`} component={NewNotice}/>
            <Route exact path={`${match.path}/:notice_category/:id`} component={NoticeDetail}/>
            <Route path={`${match.path}/:notice_category/:id/edit`} component={EditNotice}/>

        </>
    );
}

export default Notice;

