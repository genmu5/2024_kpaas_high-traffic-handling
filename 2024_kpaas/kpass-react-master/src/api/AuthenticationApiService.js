import axios from "axios";

const API_URL = process.env.REACT_APP_SERVER_URL;

// 토큰 가져오는 함수
const getAuthToken = () => {
    return localStorage.getItem("token"); // localStorage에서 토큰을 가져옵니다.
};

// 게시글 목록 가져오기 (로그인 불필요, 토큰 제외)
export const executeGetAllPosts = async (page, size) => {
    return axios.get(`${API_URL}/api/v1/posts?page=${page}&size=${size}`);
};

// 특정 게시글 가져오기 (로그인 불필요, 토큰 제외)
export const executeGetPostByPostId = async (postId) => {
    return axios.get(`${API_URL}/api/v1/posts/${postId}`);
};

// 댓글 가져오기 (로그인 필요, 토큰 불필요)
export const executeGetCommentsByPostId = async (postId) => {
    return axios.get(`${API_URL}/api/v1/comments/${postId}`);
};

// 게시글 작성 (로그인 필요, 토큰 추가)
export const executeCreatePost = async (postData) => {
    const token = getAuthToken();
    return axios.post(`${API_URL}/api/v1/posts/create`, postData, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

// 댓글 작성 (로그인 필요, 토큰 추가)
export const executeCreateComment = async (commentData) => {
    const token = getAuthToken();
    return axios.post(`${API_URL}/api/v1/comments/create`, commentData, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
};

// 최신 뉴스 가져오기 (로그인 불필요, 토큰 제외)
export const executeGetLatestNews = async () => {
    return axios.get(`${API_URL}/api/v1/navernews/latest`);
};