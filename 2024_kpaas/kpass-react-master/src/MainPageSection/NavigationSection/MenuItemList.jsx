import React, { useState, useEffect } from "react";
import styled from "styled-components";
import MenuItem from "./MenuItem";
import { useNavigate } from "react-router-dom";
import alert_icon from '../../images/alert_circle_icon.png';
import edit_icon from '../../images/edit_icon.png';
import home_icon from '../../images/home_icon.png';
import map_pin_icon from '../../images/map_pin_icon.png';
import radio_antenna_icon from '../../images/radio_antenna_icon.png';
import list_checklist_icon from '../../images/list_checklist_icon.png';
import login_icon from '../../images/login_icon.png';

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: start;
    gap: 10px;
`;

const MenuItemList = ({ onSelect }) => {
    const [selectedItem, setSelectedItem] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        setIsLoggedIn(!!token);  // 토큰이 존재하면 로그인 상태로 설정
    }, []);

    const handleSelect = (item) => {
        setSelectedItem(item);
        onSelect(item);
    };

    const handleLoginClick = () => {
        navigate('/login');  // 로그인 페이지로 이동
    };

    const handleLogoutClick = () => {
        localStorage.removeItem('token');  // localStorage에서 토큰 삭제
        setIsLoggedIn(false);  // 로그아웃 상태로 변경

        // URL에서 token 제거
        const url = new URL(window.location);
        url.searchParams.delete('token');
        window.history.pushState({}, '', url);  // 페이지를 다시 로드하지 않고 URL 업데이트

        window.location.reload();  // 페이지 새로고침 (상태 초기화)
    };

    return (
        <Container>
            <MenuItem
                title={'게시글(홈)'}
                img={home_icon}
                alt={'home icon'}
                selected={selectedItem === 'home'}
                onClick={() => handleSelect('home')}
            />
            <MenuItem
                title={'가이드'}
                img={map_pin_icon}
                alt={'map pin icon'}
                selected={selectedItem === 'guide'}
                onClick={() => handleSelect('guide')}
            />
            <MenuItem
                title={'봉사신청'}
                img={edit_icon}
                alt={'edit icon'}
                selected={selectedItem === 'volunteer'}
                onClick={() => handleSelect('volunteer')}
            />
            <MenuItem
                title={'주변대피소'}
                img={radio_antenna_icon}
                alt={'radio antenna icon'}
                selected={selectedItem === 'shelter'}
                onClick={() => handleSelect('shelter')}
            />
            <MenuItem
                title={'지역별 이슈'}
                img={alert_icon}
                alt={'alert icon'}
                selected={selectedItem === 'issue'}
                onClick={() => handleSelect('issue')}
            />
            <MenuItem
                title={'알림 확인'}
                img={list_checklist_icon}
                alt={'checklist icon'}
                selected={selectedItem === 'notification'}
                onClick={() => handleSelect('notification')}
            />

            {/* 로그인/로그아웃 메뉴 아이템 */}
            <MenuItem
                title={isLoggedIn ? '로그아웃' : '로그인/회원가입'}
                img={login_icon}  // 로그인/로그아웃 아이콘 설정
                alt={'login icon'}
                selected={selectedItem === 'auth'}
                onClick={isLoggedIn ? handleLogoutClick : handleLoginClick}
            />
        </Container>
    );
};

export default MenuItemList;
