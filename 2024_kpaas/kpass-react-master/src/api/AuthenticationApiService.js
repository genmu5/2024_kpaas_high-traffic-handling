import { apiClient } from "./ApiClient";

const getToken = () => localStorage.getItem("token");

export const executeGetAllPosts = (page, size) =>
    apiClient.get(`/api/v1/posts`, {
        params: {
            page: page,
            size: size,
        },
    });

export const executeCreatePost = (post) =>
    apiClient.post(`/api/v1/posts/create`, post, {
        headers: {
            Authorization: `Bearer ${getToken()}`, // 토큰을 헤더에 포함
        },
    });

export const executeGetLatestNews = () =>
    apiClient.get(`/api/v1/navernews/latest`);

export const executeGetCommentsByPostId = (postId) =>
    apiClient.get(`/api/v1/comments/${postId}`);

export const executeCreateComment = (comment) =>
    apiClient.post(`/api/v1/comments/create`, comment, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
    });

export const executeGetPostByPostId = (postId) =>
    apiClient.get(`/api/v1/posts/${postId}`);
