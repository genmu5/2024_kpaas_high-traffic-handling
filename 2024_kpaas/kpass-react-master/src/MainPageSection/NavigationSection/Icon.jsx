import React from "react";
import styled from "styled-components";
import smile_icon from '../../images/smile_icon.png';

const Container = styled.div`
    display: flex;
    margin-bottom: 10px;
`;

const Icon = () => {
    return(
        <Container>
            <img src={smile_icon} alt={smile_icon} width={'55px'} height={'55px'}/>
        </Container>
    );
};

export default Icon;