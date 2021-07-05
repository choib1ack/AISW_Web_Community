import React from "react";
import {Route} from "react-router-dom";
import ManageGoodInfo from "./ManageGoodInfo";
import Manager from "./Manager";

function ManagerRouter({match}) {
    return (
        <>
            <Route exact path={match.path} component={Manager}/>
            {/*<Route exact path={`${match.path}/banner`} component={NoticeList}/>*/}
            <Route exact path={`${match.path}/goodInfo`} component={ManageGoodInfo}/>
        </>
    );
}

export default ManagerRouter;

