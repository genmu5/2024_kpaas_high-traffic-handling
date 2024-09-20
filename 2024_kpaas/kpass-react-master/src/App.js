import React from "react";
import { MemoryRouter as Router, Route, Routes } from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import MainPage from "./pages/MainPage";
import HomePage from "./pages/HomePage";
import SocialLoginPage from "./pages/SocialLoginPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path='/' element={<MainPage />}/>
                {/* HomePage와 SocialLoginPage 경로 추가 */}
                <Route path="/home" element={<HomePage />} />
                <Route path="/login" element={<SocialLoginPage />} />
            </Routes>
        </Router>
    );
}

export default App;
