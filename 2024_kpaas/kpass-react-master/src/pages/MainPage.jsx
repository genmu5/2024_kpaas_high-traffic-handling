import React, { useState, useEffect } from "react";
import styled from "styled-components";
import NavigationContainer from "../MainPageSection/NavigationSection/NavigationContainer";
import SidebarContainer from "../MainPageSection/SidebarSection/SidebarContainer";
import MainContainer from "../MainPageSection/MainSection/MainContainer";
import { useNavigate } from "react-router-dom";

const Container = styled.div`
    display: flex;
    justify-content: center;
    padding: 20px 0;
    gap: 10px;
`;

const MainPage = () => {
    const [selectedMenu, setSelectedMenu] = useState('home');
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const token = urlParams.get('token'); // URL에서 'token' 추출

        if (token) {
            localStorage.setItem('token', token); // localStorage에 토큰 저장
            setIsLoggedIn(true);

            const url = new URL(window.location);
            url.searchParams.delete('token');
            window.history.pushState({}, '', url);
        } else if (localStorage.getItem('token')) {
            setIsLoggedIn(true); // localStorage에 토큰이 존재하는 경우 로그인 상태로 설정
        } else {
            setIsLoggedIn(false); // 토큰이 없는 경우 로그아웃 상태
        }
    }, []);

    return (
        <div>
            <Container>
                <NavigationContainer onSelect={setSelectedMenu} />
                <MainContainer selectedMenu={selectedMenu} />
                <SidebarContainer />
            </Container>
        </div>
    );
};

export default MainPage;
