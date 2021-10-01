import './App.css';
import Menu from "./components/Menu";
import React, {useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from "./components/Footer";
import Login from "./components/User/Login";
import Notice from "./components/Notice/Notice";
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import Home from "./components/Home";
import Board from "./components/Board/Board";
import DeptInfo from "./components/Info/DeptInfo";
import JobInfo from "./components/Info/JobInfo";
import ContestInfo from "./components/Info/ContestInfo";
import Booklet from "./components/Booklet";
import Join from "./components/User/Join";
import Banner from "./components/AdminPage/Banner/Banner";
import GoodInfo from "./components/Info/GoodInfo";
import ManagerRouter from "./components/AdminPage/ManagerRouter";
import EssentialElective from "./components/EssentialElective";
import FAQ from "./components/FAQ";
import axios from "axios";

axios.defaults.baseURL = 'http://13.125.104.47/api'

function App() {
    return (
        <Router forceRefresh={true}>
        {/*<Router>*/}
            <div className="App" style={{height: "100%"}}>
                <Menu/>

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
                            <Route path="/goodInfo" component={GoodInfo}/>
                            <Route path="/Booklet" component={Booklet}/>
                            <Route path="/GraduateCondition" component={DeptInfo}/>
                            <Route path="/EssentialElective" component={EssentialElective}/>
                            <Route path="/faq" component={FAQ}/>
                            <Route path="/join" component={Join}/>
                            <Route path="/banner" component={Banner}/>
                            <Route path="/manager" component={ManagerRouter}/>
                        </Switch>
                    </main>
                </div>
                <Footer/>
            </div>
        </Router>
    );
}

export default App;
