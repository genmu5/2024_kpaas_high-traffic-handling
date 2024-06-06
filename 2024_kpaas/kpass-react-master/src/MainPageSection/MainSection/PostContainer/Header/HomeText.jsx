import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: flex-start;
`;

const Text = styled.span`
    font-size: 24px;
    text-align: left;
    color: black;
`;

const HomeText = () => {
    return (
        <>
            <Container>
                <Text>HOME</Text>
            </Container>
        </>
    );
};

export default HomeText;