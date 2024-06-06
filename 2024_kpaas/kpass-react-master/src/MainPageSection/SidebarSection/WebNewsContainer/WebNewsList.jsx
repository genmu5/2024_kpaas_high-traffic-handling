import React from "react";
import styled from "styled-components";
import BreakingNews from "../../MainSection/PostContainer/Header/BreakingNews";
import WebNews from "./WebNews";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    border-radius: 16px;
    background: #f7f9fa;
`;

const NewsTitle = styled.span`
    padding: 10px 20px;
    font-size: 20px;
    text-align: left;
    color: #0f1419;
`;

const Divider = styled.div`
    width: 350px;
    height: 1px;
    background: #ebeef0;
`;

const WebNewsList = () => {
    return (
        <Container>
            <NewsTitle>News</NewsTitle>
            <Divider/>
            <WebNews source={"YTN"} description={"때아닌 5월 대설 주의보... 재난 문자에 인근 주민들은 ‘화들짝’"} time={"4시간 전"} tag={"#대설"}/>
            <WebNews source={"노컷뉴스"} description={"우주전파재난 ‘주의’ 위기경보... “태양 활동에 따른 자기장 교란”"} time={"2일 전"} tag={"#자기장 #우주전파재난"}/>
            <WebNews source={"광주 MBC"} description={"이상기후로 농잘물 피해... 특별재난 선포 촉구"} time={"1일 전"} tag={"#농민단체 #농작물"}/>
            <WebNews source={"뉴스1"} description={"대전시 여름철 풍수해 재난 대응 합동 훈련"} time={"2일 전"} tag={"#여름 #풍수해"}/>
        </Container>
    );
};

export default WebNewsList;