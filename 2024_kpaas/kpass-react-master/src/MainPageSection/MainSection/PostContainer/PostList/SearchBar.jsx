import React, { useState } from "react";
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

const SearchInput = styled.input`
    flex-grow: 1;
    font-size: 16px;
    border: none;
    outline: none;
    background: transparent;
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

const SearchBar = ({ onWriteClick, onSearch }) => {
    const [searchQuery, setSearchQuery] = useState("");

    const handleSearchInputChange = (event) => {
        setSearchQuery(event.target.value);
        onSearch(event.target.value);  // 검색어가 바뀔 때마다 상위 컴포넌트로 전달
    };

    return (
        <Container>
            <SearchBox>
                <IconContainer>
                    <img src={search_icon} alt="search_icon" width={'15px'} height={'15px'}/>
                </IconContainer>
                <SearchInput
                    type="text"
                    placeholder="검색어를 입력하세요"
                    value={searchQuery}
                    onChange={handleSearchInputChange}
                />
            </SearchBox>
            <WriteBox onClick={onWriteClick}>
                <WriteText>글쓰기</WriteText>
            </WriteBox>
        </Container>
    );
};

export default SearchBar;
