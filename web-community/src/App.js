import './App.css';
import Menu from "./components/Menu";
import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from "./components/Footer";
import Login from "./components/Login";
import Notice from "./components/Notice";
import Job from "./components/Job";

function App() {
    return (
        <div className="App">
            <Menu/>
                {/*<Login/>*/}
                {/*<Notice/>*/}
                <Job/>
            <Footer/>
        </div>
    );
}

export default App;
