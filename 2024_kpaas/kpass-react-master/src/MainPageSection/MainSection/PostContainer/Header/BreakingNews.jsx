import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: flex-start;
    position: relative;
    padding: 10px;
    border-radius: 9px;
    background: #cb4639;
`;

const BreakingNewsText = styled.span`
    font-size: 20px;
    text-align: left;
    color: white;
`;

const BreakingNews = () => {
    return (
        <>
            <Container>
                <BreakingNewsText>[속보] 경남 통영 오늘의 날씨, 시간당 90mm 집중호우 '침수 주의'</BreakingNewsText>
            </Container>
        </>
    );
};

export default BreakingNews;