import React from "react";
import {Route, Switch} from "react-router-dom";
import BoardList from "./BoardList";
import BoardDetail from "./BoardDetail";
import NewBoard from "./NewBoard";
import EditBoard from "./EditBoard";

function Board({match}) {
    return (
        <>
            <Route exact path={match.path} component={BoardList}/>
            <Route exact path={`${match.path}/category/prev/:board_category`} component={BoardList}/>
            <Route path={`${match.path}/newBoard`} component={NewBoard}/>
            <Route exact path={`${match.path}/:board_category/:id`} component={BoardDetail}/>
            <Route path={`${match.path}/:board_category/:id/edit`} component={EditBoard}/>
        </>
    );
}

export default Board;
