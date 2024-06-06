import React, { useState, useEffect } from 'react';
import styled from 'styled-components';

const Container = styled.div`
    display: flex;
    justify-content: space-between;
    padding: 0 20px 0 20px;
`;

const DateTimeContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 20px;
`;

const DateText = styled.span`
    font-size: 40px; /* 날짜 크기 조절 */
    text-align: center;
    color: black;
`;

const TimeText = styled.span`
    font-size: 32px;
    text-align: center;
    color: black;

    span {
        &.am {
            color: #0047ff;
        }
        &.pm {
            color: #ff0000;
        }
    }
`;

const IssueContainer = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 5px;
`;

const IssueText = styled.p`
    width: 250px;
    font-size: 14px;
    text-align: center;
    color: #1da1f2;
`;

const HotIssueBox = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    padding: 10px;
    border-radius: 16px;
    background: #c8d7ff;
`;

const HotIssueContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    position: relative;
`;

const HotIssueTitle = styled.span`
    width: 230px;
    font-size: 15px;
    text-align: left;
    padding-bottom: 10px;

    span {
        &:first-child {
            color: black;
        }

        &:last-child {
            color: #fe1212;
        }
    }
`;

const HotIssueList = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    position: relative;
    gap: 5px;
`;

const HotIssueItem = styled.span`
    font-size: 12px;
    text-align: left;
    color: #757575;
`;

const HotPosts = () => {
    const [currentTime, setCurrentTime] = useState(new Date());

    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentTime(new Date());
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    const formatTime = (date) => {
        let hours = date.getHours();
        const minutes = date.getMinutes();
        const seconds = date.getSeconds();
        const ampm = hours >= 12 ? 'PM' : 'AM';
        hours = hours % 12;
        hours = hours ? hours : 12; // the hour '0' should be '12'
        const strTime = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')} <span class="${ampm.toLowerCase()}">${ampm}</span>`;
        return strTime;
    };

    const formatDate = (date) => {
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const day = date.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    return (
        <Container>
            <DateTimeContainer>
                <DateText>{formatDate(currentTime)}</DateText>
                <TimeText dangerouslySetInnerHTML={{ __html: formatTime(currentTime) }} />
            </DateTimeContainer>
            <IssueContainer>
                <IssueText>국내 이슈들을 확인하고 공유해보세요!</IssueText>
                <HotIssueBox>
                    <HotIssueContainer>
                        <HotIssueTitle>
                            <span>실시간 게시글 Hot 3</span> <span>▲</span>
                        </HotIssueTitle>
                        <HotIssueList>
                            <HotIssueItem>1. 오늘 대구 날씨 feat.대프리카</HotIssueItem>
                            <HotIssueItem>2. 방이먹자골목에 싱크홀 생김</HotIssueItem>
                            <HotIssueItem>3. 강남 쉑쉑쪽에 차사고남</HotIssueItem>
                        </HotIssueList>
                    </HotIssueContainer>
                </HotIssueBox>
            </IssueContainer>
        </Container>
    );
};

export default HotPosts;
