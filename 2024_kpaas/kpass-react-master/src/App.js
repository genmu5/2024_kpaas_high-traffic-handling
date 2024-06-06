import React from "react";
import { MemoryRouter as Router, Route, Routes } from "react-router-dom";
import MainPage from "./pages/MainPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path='/' element={<MainPage />}/>
            </Routes>
        </Router>
    );
}

export default App;
