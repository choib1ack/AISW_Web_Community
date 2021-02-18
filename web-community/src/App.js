import './App.css';
import Menu from "./components/Menu";
import React, {useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from "./components/Footer";
import Login from "./components/Login";
import Notice from "./components/Notice/Notice";
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom";
import Home from "./components/Home";
import Board from "./components/Board/Board";
import DeptInfo from "./components/DeptInfo";
import JobInfo from "./components/JobInfo";
import ContestInfo from "./components/ContestInfo";
import Join from "./components/Join";
import CounterContainer from "./containers/CounterContainer";
import Counter from "./components/Counter";

function App() {
    return (
        <Router>
            <div className="App" style={{height: "100%"}}>
                <Menu/>

                <Counter/>

                <div style={{minHeight: "100%"}}>
                    <main>
                        <Switch>
                            <Route exact path="/" component={Home}/>
                            <Route path="/login" component={Login}/>
                            <Route path="/notice" component={Notice}/>
                            <Route path="/board" component={Board}/>
                            <Route path="/deptInfo" component={DeptInfo}/>
                            <Route path="/jobInfo" component={JobInfo}/>
                            <Route path="/contestInfo" component={ContestInfo}/>
                            <Route path="/join" component={Join}/>
                        </Switch>
                    </main>
                </div>
                <Footer/>
            </div>
        </Router>
    );
}

export default App;
