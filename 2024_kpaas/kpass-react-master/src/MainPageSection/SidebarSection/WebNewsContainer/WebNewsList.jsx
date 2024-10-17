import React, { useEffect, useState } from "react";
import styled from "styled-components";
import WebNews from "./WebNews";
import {executeGetLatestNews} from "../../../api/AuthenticationApiService";

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
    const [news, setNews] = useState([]);

    useEffect(() => {
        executeGetLatestNews()
            .then((response) => {
                if (Array.isArray(response.data)) {
                    setNews(response.data); // 응답이 배열일 경우에만 설정
                } else {
                    console.error("API 응답이 배열이 아닙니다. 응답 구조:", response.data);
                }
            })
            .catch((error) => console.error("뉴스 데이터를 가져오는 중 오류 발생:", error));
    }, []);

    return (
        <Container>
            <NewsTitle>News</NewsTitle>
            <Divider />
            {news.map((item, index) => (
                <WebNews
                    key={index}
                    title={item.title}
                    description={item.description}
                    link={item.link}
                />
            ))}
        </Container>
    );
};

export default WebNewsList;
