import React, { useState } from "react";
import MainContainer from "../MainPageSection/MainSection/MainContainer";
import ColorSchemesExample from "../components/SocialLoginNavBar";

function HomePage() {
    const [selectedMenu, setSelectedMenu] = useState('home');

    return (
        <>
            <ColorSchemesExample onSelectMenu={setSelectedMenu} />
            <MainContainer selectedMenu={selectedMenu} />
        </>
    );
}

export default HomePage;
