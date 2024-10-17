import React, { useState, useEffect, useCallback } from "react";
import styled from "styled-components";
import SearchBar from "./SearchBar";
import PostRow from "./PostRow";
import PostForm from "./PostForm";
import PostDetail from "./PostDetail";
import { executeGetAllPosts } from "../../../../api/AuthenticationApiService";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: start;
`;

const Button = styled.button`
    padding: 10px;
    font-size: 16px;
    background-color: #5abaf6;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin: 20px auto;
`;

const PostListContainer = () => {
    const [posts, setPosts] = useState([]);  // 전체 게시물
    const [filteredPosts, setFilteredPosts] = useState([]);  // 검색 결과용 게시물
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);
    const [showForm, setShowForm] = useState(false);
    const [selectedPostId, setSelectedPostId] = useState(null);
    const postsPerPage = 15;

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
                        const updatedPosts = [...prevPosts, ...newPosts];
                        setFilteredPosts(updatedPosts);  // 초기엔 전체 게시글 표시
                        return updatedPosts;
                    });
                    setHasMore(!response.data.last); // 마지막 페이지 여부 설정
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

    // 검색 기능
    const handleSearch = (searchQuery) => {
        if (searchQuery === "") {
            // 검색어가 없을 때는 전체 게시물 표시
            setFilteredPosts(posts);
        } else {
            // 검색어가 있을 때는 제목이나 내용에 검색어가 포함된 게시물 필터링
            const filtered = posts.filter(
                (post) =>
                    post.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
                    post.content.toLowerCase().includes(searchQuery.toLowerCase())
            );
            setFilteredPosts(filtered);
        }
    };

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

    const handlePostClick = (postId) => {
        setSelectedPostId(postId); // 게시물 클릭 시 해당 게시물의 ID 설정
    };

    const handleBackToList = () => {
        setSelectedPostId(null); // 상세 페이지에서 목록으로 돌아가기
    };

    // 더보기 버튼 클릭 시 호출
    const handleLoadMore = () => {
        setPage((prevPage) => prevPage + 1); // 페이지 증가
    };

    return (
        <Container>
            {showForm ? (
                <PostForm onClose={handleFormClose} onSubmit={handleFormSubmit} />
            ) : selectedPostId ? (
                <PostDetail postId={selectedPostId} onBack={handleBackToList} />
            ) : (
                <>
                    <SearchBar onWriteClick={handleWriteClick} onSearch={handleSearch} />
                    {filteredPosts.map((post, index) => (
                        <PostRow
                            key={`${post.postId}-${index}`}
                            region={post.region}
                            title={post.title}
                            createdAt={post.createdAt}
                            onClick={() => handlePostClick(post.postId)}
                        />
                    ))}
                    {loading && <p>Loading...</p>}
                    {hasMore && (
                        <Button onClick={handleLoadMore} disabled={loading}>
                            더보기
                        </Button>
                    )}
                    {!hasMore && <p style={{textAlign: "center", paddingTop: "10px"}}>게시글이 더 존재하지 않습니다.</p>}
                </>
            )}
        </Container>
    );
};

export default PostListContainer;
