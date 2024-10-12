import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { executeGetPostByPostId, executeGetCommentsByPostId, executeCreateComment } from "../../../../api/AuthenticationApiService";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f9f9f9;
`;

const CommentList = styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
`;

const CommentInput = styled.textarea`
    padding: 10px;
    font-size: 14px;
    border: 1px solid #ddd;
    border-radius: 4px;
    height: 80px;
`;

const Button = styled.button`
    padding: 10px;
    font-size: 14px;
    background-color: #5abaf6;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
`;

const PostDetail = ({ postId, onBack }) => {
    const [post, setPost] = useState(null);
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState("");
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        setIsAuthenticated(!!token);

        // 게시물 정보 가져오기
        executeGetPostByPostId(postId)
            .then((response) => {
                setPost(response.data);
            })
            .catch((error) => console.error("게시물 정보를 가져오는 중 오류가 발생했습니다:", error));

        // 댓글 가져오기
        executeGetCommentsByPostId(postId)
            .then((response) => {
                setComments(response.data);
            })
            .catch((error) => console.error("댓글을 가져오는 중 오류가 발생했습니다:", error));
    }, [postId]);

    const handleCommentSubmit = async () => {
        if (!newComment.trim()) {
            alert("댓글 내용을 입력해주세요.");
            return;
        }

        try {
            const response = await executeCreateComment({ postId, content: newComment });
            if (response.status === 200) {
                setComments((prev) => [...prev, response.data]);
                setNewComment("");
            }
        } catch (error) {
            alert("댓글 작성 중 오류가 발생했습니다.");
            console.error("Error creating comment:", error);
        }
    };

    return (
        <Container>
            <button onClick={onBack}>뒤로가기</button>
            {post && (
                <>
                    <h3>{post.title}</h3>
                    <p>{post.content}</p>
                    <hr />
                </>
            )}
            <CommentList>
                {comments.map((comment) => (
                    <div key={comment.commentId} style={{borderBottom: "1px solid #ddd"}}> {/* commentId가 null인 경우 index 사용 */}
                        <p>{comment.content}</p>
                        <small>{new Date(comment.createdAt).toLocaleString()}</small>
                    </div>
                ))}
            </CommentList>
            {isAuthenticated && (
                <>
                    <CommentInput
                        placeholder="댓글을 입력하세요"
                        value={newComment}
                        onChange={(e) => setNewComment(e.target.value)}
                    />
                    <Button onClick={handleCommentSubmit}>댓글 작성</Button>
                </>
            )}
        </Container>
    );
};

export default PostDetail;
