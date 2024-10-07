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
    const navigate = useNavigate();  // 페이지 이동을 위한 useNavigate 훅

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const token = urlParams.get('token');  // URL에서 'token' 추출

        if (token) {
            localStorage.setItem('token', token);  // localStorage에 토큰 저장
            setIsLoggedIn(true);  // 로그인 상태로 설정

            // URL에서 토큰 제거
            const url = new URL(window.location);
            url.searchParams.delete('token');
            window.history.pushState({}, '', url);  // 페이지 새로고침 없이 URL 업데이트
        } else if (localStorage.getItem('token')) {
            setIsLoggedIn(true);  // localStorage에 토큰이 존재하는 경우 로그인 상태로 설정
        } else {
            setIsLoggedIn(false);  // 토큰이 없는 경우 로그아웃 상태
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
