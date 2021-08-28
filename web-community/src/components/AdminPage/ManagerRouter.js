import React from "react";
import {Route} from "react-router-dom";
import ManageGoodInfo from "./ManageGoodInfo";
import Manager from "./Manager";
import Banner from "./Banner/Banner";
import ManageFAQ from "./ManageFAQ";
import ManageUserAuth from "./ManageUserAuth";

function ManagerRouter({match}) {
    return (
        <>
            <Route exact path={match.path} component={Manager}/>
            <Route exact path={`${match.path}/banner`} component={Banner}/>
            <Route exact path={`${match.path}/goodInfo`} component={ManageGoodInfo}/>
            <Route exact path={`${match.path}/faq`} component={ManageFAQ}/>
            <Route exact path={`${match.path}/userAuth`} component={ManageUserAuth}/>
        </>
    );
}

export default ManagerRouter;

