import React from "react";
import styled from "styled-components";

const SubTitle = styled.span`
    font-size: 18px;
    text-align: left;
    padding: 10px 20px;
    color: #0f1419;
`;

const Topic = (props) => {
    return (
        <SubTitle>{props.subTitle}</SubTitle>
    );
};

export default Topic;