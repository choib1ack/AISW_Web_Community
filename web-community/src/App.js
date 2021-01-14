import './App.css';
import Menu from "./components/Menu";
import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Footer from "./components/Footer";

function App() {
    return (
        <div className="App">
            <Menu/>
            <Footer/>
        </div>
    );
}

export default App;
