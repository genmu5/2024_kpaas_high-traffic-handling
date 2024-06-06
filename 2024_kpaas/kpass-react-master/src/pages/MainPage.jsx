import React, {useState} from "react";
import styled from "styled-components";
import NavigationContainer from "../MainPageSection/NavigationSection/NavigationContainer";
import SidebarContainer from "../MainPageSection/SidebarSection/SidebarContainer";
import MainContainer from "../MainPageSection/MainSection/MainContainer";

const Container = styled.div`
    display: flex;
    justify-content: center;
    padding: 20px 0;
    gap: 10px;
`;

const MainPage = () => {

    const [selectedMenu, setSelectedMenu] = useState('home');

    return (
        <Container>
            <NavigationContainer onSelect={setSelectedMenu} />
            <MainContainer selectedMenu={selectedMenu} />
            <SidebarContainer/>
        </Container>
    );
};

export default MainPage;