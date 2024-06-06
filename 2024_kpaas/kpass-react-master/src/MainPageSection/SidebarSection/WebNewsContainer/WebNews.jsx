import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: start;
    align-items: center;
    position: relative;
    gap: 15px;
    padding: 10px 15px;
`;

const NewsContent = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: start;
    gap: 5px;
`;

const Source = styled.span`
    font-size: 14px;
    text-align: left;
    color: #5b7083;
`;

const Description = styled.span`
    width: 235px;
    font-size: 15px;
    text-align: left;
    color: #0f1419;
`;

const MetaData = styled.div`
    display: flex;
    justify-content: flex-start;
    align-items: center;
    gap: 4px;
`;

const Time = styled.span`
    font-size: 14px;
    text-align: left;
    color: #5b7083;
`;

const Tag = styled.span`
    font-size: 14px;
    text-align: left;
    color: #1da1f2;
`;

const ImageContainer = styled.div`
    width: 95px;
    height: 95px;
`;

const NewsImage = styled.img`
    width: 95px;
    height: 95px;
`;

const WebNews = (props) => {
    return (
        <Container>
            <NewsContent>
                <Source>{props.source}</Source>
                <Description>{props.description}</Description>
                <MetaData>
                    <Time>{props.time}</Time>
                    <Tag>{props.tag}</Tag>
                </MetaData>
            </NewsContent>
            <ImageContainer>
                <NewsImage src="image-13.png" alt="news"/>
            </ImageContainer>
        </Container>
    );
};

export default WebNews;