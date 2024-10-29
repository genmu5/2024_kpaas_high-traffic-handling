import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const PageContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #6ca0dc; /* 전체 화면 배경을 파란색으로 설정 */
`;

const LoginBox = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 400px; /* 박스 너비를 키움 */
    padding: 50px; /* 패딩을 늘려 박스 크기를 키움 */
    border-radius: 10px;
    background-color: #ffffff; /* 로그인 박스 배경을 흰색으로 설정 */
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
`;

const Input = styled.input`
    margin: 10px 0;
    padding: 10px;
    width: 80%; /* 입력 필드의 가로 폭을 박스 크기에 맞게 조정 */
    max-width: 300px;
`;

const Button = styled.button`
    background-color: #ff6b6b;
    color: white;
    padding: 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 10px;
    width: 80%; /* 버튼 폭을 입력 필드와 일치시키기 */
    max-width: 300px;
`;

const LoginPage = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const response = await fetch(`${process.env.REACT_APP_SERVER_URL}/api/v1/auth/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email: username, password: password }),
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem("token", data.token);
                navigate("/");
            } else {
                alert("로그인 실패");
            }
        } catch (error) {
            console.error("로그인 중 오류 발생:", error);
        }
    };

    return (
        <PageContainer>
            <LoginBox>
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
            </LoginBox>
        </PageContainer>
    );
};

export default LoginPage;

