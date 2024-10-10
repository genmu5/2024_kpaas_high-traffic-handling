import React, { useState, useEffect, useCallback } from "react";
import styled from "styled-components";
import SearchBar from "./SearchBar";
import PostRow from "./PostRow";
import PostForm from "./PostForm";
import { executeGetAllPosts } from "../../../../api/AuthenticationApiService";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: start;
`;

const PostListContainer = () => {
    const [posts, setPosts] = useState([]);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);
    const [showForm, setShowForm] = useState(false); // 글쓰기 폼 표시 여부
    const postsPerPage = 9;

    const fetchPosts = useCallback(async (page) => {
        setLoading(true);
        try {
            const response = await executeGetAllPosts(page, postsPerPage);
            if (response.status === 200) {
                const { content } = response.data;
                if (Array.isArray(content)) {
                    setPosts((prevPosts) => {
                        const newPosts = content.filter(
                            (post) => !prevPosts.some((p) => p.postId === post.postId)
                        );
                        return [...prevPosts, ...newPosts];
                    });
                    setHasMore(!response.data.last);
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
        fetchPosts(page);
    }, [fetchPosts, page]);

    const handleWriteClick = () => {
        setShowForm(true);
    };

    const handleFormClose = () => {
        setShowForm(false);
    };

    const handleFormSubmit = () => {
        setPosts([]); // 게시글 목록 초기화
        setPage(0); // 첫 페이지부터 다시 로드
        fetchPosts(0);
        setShowForm(false); // 글 작성 후 폼 닫기
    };

    return (
        <Container>
            {showForm ? (
                <PostForm onClose={handleFormClose} onSubmit={handleFormSubmit} />
            ) : (
                <>
                    <SearchBar onWriteClick={handleWriteClick} />
                    {posts.map((post, index) => (
                        <PostRow
                            key={`${post.postId}-${index}`}
                            region={post.region}
                            title={post.title}
                            createdAt={post.createdAt}
                        />
                    ))}
                    {loading && <p>Loading...</p>}
                    {!hasMore && <p>No more posts available</p>}
                </>
            )}
        </Container>
    );
};

export default PostListContainer;
