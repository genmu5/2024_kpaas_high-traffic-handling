import React, {useState} from "react";
import styled from "styled-components";
import Icon from "./Icon";
import MenuItemList from "./MenuItemList";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: start;
    width: 230px;
    height: auto;
    margin: 0 10px 0 10px;
    gap: 10px;
`;

const NavigationContainer = ({ onSelect }) => {
    return (
        <Container>
            <Icon />
            <MenuItemList onSelect={onSelect} />
        </Container>
    );
};

export default NavigationContainer;
