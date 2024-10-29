import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const SignUpContainer = styled.div`
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

const SignUpPage = () => {
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [rePassword, setRePassword] = useState("");
    const navigate = useNavigate();

    const handleSignUp = async () => {
        if (password === rePassword) {
            try {
                const response = await fetch("http://localhost:8080/api/v1/auth/register", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        email: email,
                        name: username,
                        password: password,
                        passwordConfirm: rePassword
                    }),
                });

                if (response.ok) {
                    const message = await response.text(); // 응답을 텍스트로 처리
                    if (message === "User registered successfully") {
                        navigate("/login"); // 회원가입 성공 시 로그인 페이지로 이동
                    } else {
                        alert("회원가입 실패: " + message);
                    }
                } else {
                    alert("회원가입 실패");
                }
            } catch (error) {
                console.error("회원가입 중 오류 발생:", error);
            }
        } else {
            alert("비밀번호가 일치하지 않습니다!");
        }
    };


    return (
        <SignUpContainer>
            <h1>SIGN UP</h1>
            <Input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
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
            <Input
                type="password"
                placeholder="Re Password"
                value={rePassword}
                onChange={(e) => setRePassword(e.target.value)}
            />
            <Button onClick={handleSignUp}>Sign Up</Button>
            <Button onClick={() => navigate("/login")}>이미 가입 되어 있으신가요?</Button>
        </SignUpContainer>
    );
};

export default SignUpPage;
