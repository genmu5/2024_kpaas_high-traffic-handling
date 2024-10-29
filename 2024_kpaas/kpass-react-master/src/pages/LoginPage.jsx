import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const LoginContainer = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    background-color: #6ca0dc; /* 배경 색상 변경 */
`;

const Input = styled.input`
    margin: 10px 0;
    padding: 10px;
    width: 60%; /* 입력 필드의 가로 폭을 줄임 */
    max-width: 300px; /* 최대 너비 제한 */
`;

const Button = styled.button`
    background-color: #ff6b6b;
    color: white;
    padding: 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 10px;
    width: 60%; /* 버튼 폭을 입력 필드와 일치시키기 */
    max-width: 300px; /* 최대 너비 제한 */
`;

const LoginPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/v1/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email: username, password: password }), // 이메일과 비밀번호 전송
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem("token", data.token); // JWT 토큰을 로컬 스토리지에 저장
                navigate("/"); // 로그인 성공 시 메인 페이지로 이동
            } else {
                alert("로그인 실패");
            }
        } catch (error) {
            console.error("로그인 중 오류 발생:", error);
        }
    };


    return (
        <LoginContainer>
            <h1>LOGIN</h1>
            <Input
                type="text"
                placeholder="User Name"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <Input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <Button onClick={handleLogin}>로그인</Button>
            <Button onClick={() => navigate("/signup")}>회원 가입</Button>
        </LoginContainer>
    );
};

export default LoginPage;
