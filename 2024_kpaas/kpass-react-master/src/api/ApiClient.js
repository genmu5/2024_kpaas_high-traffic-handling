import axios from "axios";

// 환경 변수에서 API URL 가져오기
const baseURL = process.env.REACT_APP_API_URL || 'http://localhost:8080';  // 로컬 개발 환경을 위한 기본값

export const apiClient = axios.create({
    baseURL: baseURL  // 동적으로 할당된 백엔드 URL 사용
});