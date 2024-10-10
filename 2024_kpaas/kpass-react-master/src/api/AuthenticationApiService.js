import { apiClient } from "./ApiClient";

export const executeGetAllPosts = (page, size) =>
    apiClient.get(`/api/v1/posts`, {
        params: {
            page: page,
            size: size,
        },
    });

export const executeCreatePost = (post) =>
    apiClient.post(`/api/v1/posts/create`, post);


export const executeGetPostByUUID
    = (postUUID) => apiClient.get(`/api/v1/posts/${postUUID}`, {postUUID})

