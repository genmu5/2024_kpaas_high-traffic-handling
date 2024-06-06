import React from "react";
import styled, {css} from "styled-components";

const Container = styled.div`
    display: flex;
    height: 40px;
    align-items: center;
    gap: 10px;
    padding: 5px 8px;
    border-radius: 16px;
    cursor: pointer;
    transition: background-color 0.3s, transform 0.3s;

    ${({ selected }) =>
            selected &&
            css`
            background-color: #ebeef0;
            transform: scale(1.05);
        `}

    &:hover {
        background-color: #f0f0f0;
        transform: scale(1.05);
    }
`;

const Text = styled.span`
  font-size: 19px;
  text-align: left;
  color: #1da1f2;
`;

const MenuItem = (props) => {
    return(
        <Container selected={props.selected} onClick={props.onClick}>
            <img src={props.img} alt={props.alt} width={'30px'} height={'30px'} />
            <Text>{props.title}</Text>
        </Container>
    );
};

export default MenuItem;