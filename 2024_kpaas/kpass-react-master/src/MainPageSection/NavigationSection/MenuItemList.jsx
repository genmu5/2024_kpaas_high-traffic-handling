import React, { useState } from "react";
import styled from "styled-components";
import MenuItem from "./MenuItem";
import alert_icon from '../../images/alert_circle_icon.png';
import edit_icon from '../../images/edit_icon.png';
import home_icon from '../../images/home_icon.png';
import map_pin_icon from '../../images/map_pin_icon.png';
import radio_antenna_icon from '../../images/radio_antenna_icon.png';
import list_checklist_icon from '../../images/list_checklist_icon.png';

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: start;
    gap: 10px;
`;

const MenuItemList = ({ onSelect }) => {
    const [selectedItem, setSelectedItem] = useState(null);

    const handleSelect = (item) => {
        setSelectedItem(item);
        onSelect(item);
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
        </Container>
    );
};

export default MenuItemList;
