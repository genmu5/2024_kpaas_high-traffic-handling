import React from "react";
import styled from "styled-components";
import Topic from "./Topic";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    border-radius: 16px;
    background: #f7f9fa;
`;

const Title = styled.span`
    padding: 10px 20px;
    font-size: 25px;
    text-align: left;
    color: #0f1419;
`;

const Divider = styled.div`
    width: 350px;
    height: 1px;
    background: #ebeef0;
`;

const TopicList = () => {
    return (
        <Container>
            <Title>인기토픽</Title>
            <Divider/>
            <Topic subTitle={"1. 코로나바이러스"}/>
            <Topic subTitle={"2. 일본 지진"}/>
            <Topic subTitle={"3. 폭염"}/>
            <Topic subTitle={"4. 태풍"}/>
            <Topic subTitle={"5. 이상기후"}/>
        </Container>
    );
};

export default TopicList;
