import React, { useState } from "react";
import styled from "styled-components";
import HeadTable from "./HeadTable";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    width: 630px;
    height: auto;
    margin: 0 10px 0 10px;
    gap: 10px;
`;

const GuideContainer = () => {
    const [selectedTopic, setSelectedTopic] = useState(null);

    const handleItemClick = (item) => {
        setSelectedTopic(item);
    };

    const renderGuide = () => {
        switch (selectedTopic) {
            case '침수':
                return <p>침수에 대한 가이드 내용</p>;
            case '태풍':
                return <p>태풍에 대한 가이드 내용</p>;
            case '지진':
                return <p>지진에 대한 가이드 내용</p>;
            case '홍수':
                return <p>홍수에 대한 가이드 내용</p>;
            case '호우':
                return <p>호우에 대한 가이드 내용</p>;
            case '낙뢰':
                return <p>낙뢰에 대한 가이드 내용</p>;
            case '해일':
                return <p>해일에 대한 가이드 내용</p>;
            case '대설':
                return <p>대설에 대한 가이드 내용</p>;
            case '폭염':
                return <p>폭염에 대한 가이드 내용</p>;
            case '황사':
                return <p>황사에 대한 가이드 내용</p>;
            case '한파':
                return <p>한파에 대한 가이드 내용</p>;
            default:
                return <p>가이드를 선택해주세요.</p>;
        }
    };

    return (
        <Container>
            <h1>GuideContainer</h1>
            <HeadTable onItemClick={handleItemClick} />
            <div>
                {renderGuide()}
            </div>
        </Container>
    );
};

export default GuideContainer;
