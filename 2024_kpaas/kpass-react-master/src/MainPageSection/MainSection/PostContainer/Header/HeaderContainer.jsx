import React from "react";
import HotPosts from "./HotPosts";
import BreakingNews from "./BreakingNews";
import HomeText from "./HomeText";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 10px;
`;

const HeaderContainer = () => {
    return (
        <Container>
            <HomeText/>
            <HotPosts/>
            {/*<BreakingNews/>*/}
        </Container>
    );
};

export default HeaderContainer;