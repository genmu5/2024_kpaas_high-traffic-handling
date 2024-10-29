import React from "react";
import { MemoryRouter as Router, Route, Routes } from "react-router-dom";
import { RecoilRoot } from 'recoil';

import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import MainPage from "./pages/MainPage";
import HomePage from "./pages/HomePage";
import SocialLoginPage from "./pages/SocialLoginPage";
import LoginPage from "./pages/LoginPage";
import SignUpPage from "./pages/SignUpPage";

function App() {
    return (
        <RecoilRoot>
            <Router>
                <Routes>
                    <Route path='/' element={<MainPage />}/>
                    <Route path='/login' element={<LoginPage />} />  {/* 로그인 페이지 경로 */}
                    <Route path='/signup' element={<SignUpPage />} />  {/* 회원가입 페이지 경로 */}
                </Routes>
            </Router>
        </RecoilRoot>
    );
}

export default App;
