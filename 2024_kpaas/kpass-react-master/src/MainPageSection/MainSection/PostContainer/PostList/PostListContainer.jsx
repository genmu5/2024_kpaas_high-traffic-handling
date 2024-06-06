import React, { useState, useEffect } from "react";
import styled from "styled-components";
import SearchBar from "./SearchBar";
import PostRow from "./PostRow";
import { executeGetAllPosts } from "../../../../api/AuthenticationApiService";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: start;
`;

const Pagination = styled.div`
    display: flex;
    justify-content: center;
    margin-top: 20px;
`;

const PageButton = styled.button`
    margin: 0 5px;
    padding: 5px 10px;
    border: none;
    background-color: #f0f0f0;
    cursor: pointer;

    &:disabled {
        cursor: not-allowed;
        opacity: 0.5;
    }
`;

const PostListContainer = () => {
    const [posts, setPosts] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [postsPerPage] = useState(9);

    const fetchPosts = async () => {
        try {
            const response = await executeGetAllPosts();
            if (response.status === 200) {
                const sortedPosts = response.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
                setPosts(sortedPosts);
            }
        } catch (error) {
            console.error("Error fetching posts:", error);
        }
    };

    useEffect(() => {
        fetchPosts();
    }, []);

    // Get current posts
    const indexOfLastPost = currentPage * postsPerPage;
    const indexOfFirstPost = indexOfLastPost - postsPerPage;
    const currentPosts = posts.slice(indexOfFirstPost, indexOfLastPost);

    // Change page
    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <Container>
            <SearchBar />
            {currentPosts.map((post) => (
                <PostRow
                    key={post.postUUID}
                    region={post.region}
                    title={post.title}
                    createdAt={post.createdAt}
                />
            ))}
            <Pagination>
                {[...Array(Math.ceil(posts.length / postsPerPage)).keys()].map(number => (
                    <PageButton
                        key={number + 1}
                        onClick={() => paginate(number + 1)}
                        disabled={currentPage === number + 1}
                    >
                        {number + 1}
                    </PageButton>
                ))}
            </Pagination>
        </Container>
    );
};

export default PostListContainer;
