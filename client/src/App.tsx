import React from 'react';
import {GlobalStyle} from "./components/GlobalStyle/GlobalStyle";
import NavBar from "./components/NavBar/NavBar";
import GifAnimation from "./components/Gif/GifAnimation";
import ContentIndex from "./components/IndexPageContent/ContentIndex/ContentIndex";

const App: React.FC = () => {

    return (
        <>
            <GlobalStyle/>
            <NavBar/>
            <GifAnimation indexPageVideo={true}/>
            <ContentIndex/>
        </>
    );
}

export default App;
