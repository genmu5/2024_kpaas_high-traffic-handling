import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    padding: 5px 0;
    justify-content: flex-start;
    gap: 2px;
    border-bottom: darkgray solid 1px;
`;

const PostTitleContainer = styled.div`
    display: flex;
    justify-content: flex-start;
    align-items: center;
    gap: 10px;
`;

const Text = styled.span`
    font-size: 15px;
    text-align: left;
`;

const HighlightText = styled.span`
    font-size: 15px;
    text-align: left;
    color: #5abaf6;
`;

const NormalText = styled.span`
    font-size: 15px;
    text-align: left;
    color: #000;
`;

const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
};

const PostRow = (props) => {
    return (
        <Container onClick={props.onClick}>
            <PostTitleContainer>
                <Text>
                    <HighlightText>#{props.region}</HighlightText>
                    <NormalText> </NormalText>
                </Text>
                <Text>
                    <NormalText>{props.title}</NormalText>
                </Text>
            </PostTitleContainer>
            <Text>
                <NormalText>{formatDate(props.createdAt)}</NormalText>
            </Text>
        </Container>
    );
};

export default PostRow;
