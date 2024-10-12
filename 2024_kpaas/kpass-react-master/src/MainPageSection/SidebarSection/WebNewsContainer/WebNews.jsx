import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: start;
    align-items: center;
    position: relative;
    gap: 15px;
    padding: 10px 15px;
    padding: 10px 15px;
    cursor: pointer;
    border: 1px solid whitesmoke;
`;

const NewsContent = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: start;
    gap: 5px;
`;

const Title = styled.span`
    font-size: 15px;
    text-align: left;
    font-weight: bold;
    color: #0f1419;
`;

const Description = styled.span`
    width: 235px;
    font-size: 14px;
    text-align: left;
    color: #5b7083;
`;

const truncateText = (text, maxLength) => {
    if (text.length > maxLength) {
        return `${text.substring(0, maxLength)}...`;
    }
    return text;
};

const WebNews = ({ title, description, link }) => {
    return (
        <Container onClick={() => window.open(link, "_blank")}>
            <NewsContent>
                <Title>{title}</Title>
                <Description>{truncateText(description, 40)}</Description> {/* 최대 100글자 */}
            </NewsContent>
        </Container>
    );
};

export default WebNews;
