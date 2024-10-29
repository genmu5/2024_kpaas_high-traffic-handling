import React from "react";
import styled from "styled-components";
import smile_icon from '../../images/homepage_black.png';
const Container = styled.div`
    display: flex;
    margin-bottom: 10px;
`;

const Icon = () => {
    return(
        <Container>
            <img src={smile_icon} alt={smile_icon} width={'80px'} height={'80px'}/>
        </Container>
    );
};

export default Icon;