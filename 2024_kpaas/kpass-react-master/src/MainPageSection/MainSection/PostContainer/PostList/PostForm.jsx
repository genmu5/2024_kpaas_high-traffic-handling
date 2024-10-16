import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { executeCreatePost } from "../../../../api/AuthenticationApiService";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 16px;
    background-color: #f9f9f9;
`;

const Input = styled.input`
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ddd;
    border-radius: 4px;
`;

const TextArea = styled.textarea`
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ddd;
    border-radius: 4px;
    height: 100px;
`;

const Select = styled.select`
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ddd;
    border-radius: 4px;
`;

const Button = styled.button`
    padding: 10px;
    font-size: 16px;
    background-color: #5abaf6;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
`;

const PostForm = ({ onClose, onSubmit }) => {
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [region, setRegion] = useState("서울특별시");
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            setIsAuthenticated(true); // 토큰이 존재하면 인증된 것으로 간주
        }
    }, []);

    const regions = [
        "서울특별시",
        "부산광역시",
        "대구광역시",
        "인천광역시",
        "광주광역시",
        "대전광역시",
        "울산광역시",
        "세종특별자치시",
        "경기도",
        "강원특별자치도",
        "충청북도",
        "충청남도",
        "전북특별자치도",
        "전라남도",
        "경상북도",
        "경상남도",
        "제주특별자치도"
    ];

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!isAuthenticated) {
            alert("로그인이 필요합니다. 로그인 후 다시 시도해주세요.");
            return;
        }

        try {
            const response = await executeCreatePost({
                title,
                content,
                region,
                likeCount: 0
            });
            if (response.status === 200) {
                alert("게시글이 성공적으로 저장되었습니다.");
                onSubmit();
                onClose();
            } else {
                alert("게시글 저장에 실패했습니다.");
            }
        } catch (error) {
            alert("게시글 저장 중 오류가 발생했습니다.");
            console.error("Error creating post:", error);
        }
    };

    return (
        <Container>
            <h3>새 글 작성</h3>
            <Input
                type="text"
                placeholder="제목을 입력하세요"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />
            <TextArea
                placeholder="내용을 입력하세요"
                value={content}
                onChange={(e) => setContent(e.target.value)}
            />
            <Select value={region} onChange={(e) => setRegion(e.target.value)}>
                {regions.map((r) => (
                    <option key={r} value={r}>
                        {r}
                    </option>
                ))}
            </Select>
            <Button onClick={handleSubmit}>작성 완료</Button>
            <Button onClick={onClose} style={{ backgroundColor: "#ddd", marginTop: "10px" }}>목록으로</Button>
        </Container>
    );
};

export default PostForm;
