import { apiClient } from "./ApiClient";

export const executeGetAllPosts
    = () => apiClient.get(`/api/v1/posts`)

export const executeGetPostByUUID
    = (postUUID) => apiClient.get(`/api/v1/posts/${postUUID}`, {postUUID})