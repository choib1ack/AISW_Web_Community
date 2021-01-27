import React from "react";
import {Route} from "react-router-dom";
import BoardList from "./BoardList";
import BoardDetail from "./BoardDetail";
import NewBoard from "./NewBoard";

function Board({match}) {
    return (
        <>
            <Route exact path={match.path} component={BoardList}/>
            <Route path={`${match.path}/:id`} component={BoardDetail}/>
            <Route path='/newBoard' component={NewBoard}/>
        </>
    );
}

export default Board;
