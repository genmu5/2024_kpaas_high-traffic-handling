import React from "react";
import HeaderContainer from "./Header/HeaderContainer";
import styled from "styled-components";
import PostListContainer from "./PostList/PostListContainer";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    width: 630px;
    height: auto;
    margin: 0 10px 0 10px;
    gap: 10px;
`;

const PostContainer = () => {
    return (
        <Container>
            <HeaderContainer/>
            <PostListContainer/>
        </Container>
    );
};

export default PostContainer;
