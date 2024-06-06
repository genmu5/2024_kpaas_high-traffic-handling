import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    width: 630px;
    height: auto;
    margin: 0 10px 0 10px;
    gap: 10px;
`;

const NotificationContainer = () => {
    return (
        <Container>
            <h1>NotificationContainer</h1>
        </Container>
    );
};

export default NotificationContainer;
