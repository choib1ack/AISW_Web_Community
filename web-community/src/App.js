import './App.css';
import Menu from "./components/Menu";
import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from "./components/Footer";
import Login from "./components/Login";
import Notice from "./components/Notice";
import Job from "./components/Job";
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom";
import Home from "./components/Home";
import NoticeDetail from "./components/NoticeDetail";
import BoardDetail from "./components/BoardDetail";

function App() {
    return (
        <Router>
            <div className="App">
                <Menu/>

                <NoticeDetail/>
                <BoardDetail/>
                {/*<Switch>*/}
                {/*    <Route exact path="/">*/}
                {/*        <Home/>*/}
                {/*    </Route>*/}
                {/*</Switch>*/}

                <Footer/>
            </div>
        </Router>
    );
}

export default App;
