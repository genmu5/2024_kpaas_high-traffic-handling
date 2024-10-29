import React from "react";
import styled from "styled-components";
import PostContainer from "./PostContainer/PostContainer";
import GuideContainer from "./GuideContainer/GuideContainer";
import ShelterContainer from "./ShelterContainer/ShelterContainer";
import IssueContainer from "./IssueContainer/IssueContainer";


const MainContainer = ({ selectedMenu }) => {
    return(
        <>
            {selectedMenu === 'home' && <PostContainer />}
            {selectedMenu === 'guide' && <GuideContainer />}
            {selectedMenu === 'shelter' && <ShelterContainer />}
            {selectedMenu === 'issue' && <IssueContainer />}
        </>
    );
};

export default MainContainer;