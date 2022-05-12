import "./App.css"
import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import AnotherHome from "./components/AnotherHome";
import Login from "./components/Login";
import NavigationBar from "./components/NavigationBarClass";

const API_URL = 'http://localhost:8081/api/v1'
const AUTH_URL = 'http://localhost:8081/auth'

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <NavigationBar />
                <div className="container-fluid">
                    <Routes>
                        <Route path="home" element={<Home />} />
                        <Route path="home2" element={<AnotherHome />} />
                        <Route path="Login" element={<Login />} />
                    </Routes>
                </div>
            </BrowserRouter>
        </div>
    );
}

export default App;
