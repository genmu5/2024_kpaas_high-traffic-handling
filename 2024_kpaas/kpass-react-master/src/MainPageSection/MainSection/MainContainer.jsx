import React from "react";
import styled from "styled-components";
import PostContainer from "./PostContainer/PostContainer";
import GuideContainer from "./GuideContainer/GuideContainer";
import VolunteerContainer from "./VolunteerContainer/VolunteerContainer";
import ShelterContainer from "./ShelterContainer/ShelterContainer";
import IssueContainer from "./IssueContainer/IssueContainer";
import NotificationContainer from "./NotificationContainer/NotificationContainer";

const MainContainer = ({ selectedMenu }) => {
    return(
        <>
            {selectedMenu === 'home' && <PostContainer />}
            {selectedMenu === 'guide' && <GuideContainer />}
            {selectedMenu === 'volunteer' && <VolunteerContainer />}
            {selectedMenu === 'shelter' && <ShelterContainer />}
            {selectedMenu === 'issue' && <IssueContainer />}
            {selectedMenu === 'notification' && <NotificationContainer />}
        </>
    );
};

export default MainContainer;