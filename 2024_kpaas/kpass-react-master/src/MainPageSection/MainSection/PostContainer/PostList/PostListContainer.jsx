import React, { useState, useEffect, useCallback } from "react";
import styled from "styled-components";
import SearchBar from "./SearchBar";
import PostRow from "./PostRow";
import { executeGetAllPosts } from "../../../../api/AuthenticationApiService";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: start;
`;

const LoadingMessage = styled.p`
    text-align: center;
    margin-top: 20px;
`;

const EndMessage = styled.p`
    text-align: center;
    margin-top: 20px;
`;

const PostListContainer = () => {
    const [posts, setPosts] = useState([]);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);
    const postsPerPage = 9;

    const fetchPosts = useCallback(async (page) => {
        setLoading(true);
        try {
            const response = await executeGetAllPosts(page, postsPerPage);
            if (response.status === 200) {
                const { content } = response.data;
                if (Array.isArray(content)) {
                    // 중복되지 않는 포스트만 추가
                    setPosts((prevPosts) => {
                        const newPosts = content.filter(
                            (post) => !prevPosts.some((p) => p.postId === post.postId)
                        );
                        return [...prevPosts, ...newPosts];
                    });
                    setHasMore(!response.data.last); // 마지막 페이지인지 확인
                } else {
                    console.error("응답 데이터가 배열 형식이 아닙니다.");
                }
            }
        } catch (error) {
            console.error("게시물을 가져오는 중 오류가 발생했습니다:", error);
        } finally {
            setLoading(false);
        }
    }, [postsPerPage]);

    useEffect(() => {
        // 페이지가 변경될 때마다 fetchPosts 호출
        fetchPosts(page);
    }, [fetchPosts, page]);

    const handleScroll = useCallback(() => {
        if (
            window.innerHeight + document.documentElement.scrollTop + 1 >= document.documentElement.scrollHeight &&
            hasMore &&
            !loading
        ) {
            setPage((prevPage) => prevPage + 1);
        }
    }, [hasMore, loading]);

    useEffect(() => {
        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, [handleScroll]);

    return (
        <Container>
            <SearchBar />
            {posts.map((post, index) => (
                <PostRow
                    key={`${post.postId}-${index}`} // 고유한 키를 위해 인덱스를 추가
                    region={post.region}
                    title={post.title}
                    createdAt={post.createdAt}
                />
            ))}
            {loading && <LoadingMessage>Loading...</LoadingMessage>}
            {!hasMore && <EndMessage>No more posts available</EndMessage>}
        </Container>
    );
};

export default PostListContainer;
