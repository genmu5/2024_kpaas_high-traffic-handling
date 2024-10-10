import React from "react";
import styled from "styled-components";
import search_icon from '../../../../images/search_icon.png';

const Container = styled.div`
    display: flex;
    justify-content: space-between;
    gap: 10px;
    margin-bottom: 10px;
`;

const SearchBox = styled.div`
    display: flex;
    justify-content: flex-start;
    align-items: center;
    align-self: stretch;
    position: relative;
    overflow: hidden;
    width: 100%;
    gap: 5px;
    padding: 10px 16px;
    border-radius: 99999px;
    background: #ebeef0;
`;

const IconContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    flex-grow: 0;
    flex-shrink: 0;
    overflow: hidden;
    gap: 10px;
    padding: 2px;
`;

const SearchText = styled.span`
    flex-grow: 0;
    flex-shrink: 0;
    font-size: 16px;
    text-align: left;
    color: #5b7083;
`;

const WriteBox = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80px;
    padding: 5px 10px;
    border-radius: 14px;
    background: #dff1ff;
    cursor: pointer;
`;

const WriteText = styled.span`
    font-size: 16px;
    text-align: center;
    color: black;
`;

const SearchBar = ({ onWriteClick }) => {
    return (
        <Container>
            <SearchBox>
                <IconContainer>
                    <img src={search_icon} alt="like_icon" width={'15px'} height={'15px'}/>
                </IconContainer>
                <SearchText>Search</SearchText>
            </SearchBox>
            <WriteBox onClick={onWriteClick}>
                <WriteText>글쓰기</WriteText>
            </WriteBox>
        </Container>
    );
};

export default SearchBar;
