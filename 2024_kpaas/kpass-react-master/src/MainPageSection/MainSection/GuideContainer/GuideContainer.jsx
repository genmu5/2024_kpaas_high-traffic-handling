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
                return(
                    <div>
                        <img src={process.env.PUBLIC_URL + "/images/국민행동요령_침수_지하.jpg"} alt="침수_지하_행동강령" style={{ width: '100%', height: 'auto' }} />
                        <img src={process.env.PUBLIC_URL + "/images/국민행동요령_침수_차량.jpg"} alt="침수_차량_행동강령" style={{ width: '100%', height: 'auto' }} />
                    </div>
                );
            case '태풍':
                return <img src={process.env.PUBLIC_URL + "/images/국민행동요령_태풍호우.jpg"} alt="태풍행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '지진':
                return <img src={process.env.PUBLIC_URL + "/images/지진.png"} alt="지진행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '홍수':
                return <img src={process.env.PUBLIC_URL + "/images/홍수.png"} alt="홍수행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '대설':
                return <img src={process.env.PUBLIC_URL + "/images/대설.jpg"} alt="대설행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '낙뢰':
                return <img src={process.env.PUBLIC_URL + "/images/낙뢰.png"} alt="낙뢰행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '해일':
                return <img src={process.env.PUBLIC_URL + "/images/해일.png"} alt="해일행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '우주물체 추락_충돌':
                return <img src={process.env.PUBLIC_URL + "/images/우주물체.png"} alt="우주물체 추락_충돌행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '폭염':
                return <img src={process.env.PUBLIC_URL + "/images/폭염.png"} alt="폭염행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '한파':
                return <img src={process.env.PUBLIC_URL + "/images/한파.jpg"} alt="한파행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '가뭄':
                return <img src={process.env.PUBLIC_URL + "/images/가뭄.png"} alt="가뭄행동강령" style={{ width: '100%', height: 'auto' }} />;
            case '우주전파':
                return (
                    <div>
                        <img src={process.env.PUBLIC_URL + "/images/우주전파_1.png"} alt="우주전파행동강령" style={{ width: '100%', height: 'auto' }} />;
                        <img src={process.env.PUBLIC_URL + "/images/우주전파_2.png"} alt="우주전파행동강령" style={{ width: '100%', height: 'auto' }} />;
                    </div>
                );
            default:
                return <p>가이드를 선택해주세요.</p>;
        }
    };

    return (
        <Container>
            <h1>재난 가이드</h1>
            <HeadTable onItemClick={handleItemClick}/>
            <div>
                {renderGuide()}
            </div>
        </Container>
    );
};

export default GuideContainer;
