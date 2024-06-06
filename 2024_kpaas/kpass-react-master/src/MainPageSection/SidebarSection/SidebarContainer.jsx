import React from "react";
import styled from "styled-components";
import TopicList from "./TopicContainer/TopicList";
import WebNewsList from "./WebNewsContainer/WebNewsList";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 350px;
    height: auto;
    margin: 0 10px 0 10px;
    gap: 10px;
`;

const SidebarContainer = () => {
    return (
        <Container>
            <TopicList/>
            <WebNewsList/>
        </Container>
    );
};

export default SidebarContainer;