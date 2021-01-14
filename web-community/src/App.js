import './App.css';
import Menu from "./components/Menu";
import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from "./components/Footer";
import Login from "./components/Login";

function App() {
    return (
        <div className="App">
            <Menu/>
            <Login/>
            <Footer/>
        </div>
    );
}

export default App;
