import React from 'react';
import {GlobalStyle} from "./components/GlobalStyle/GlobalStyle";
import NavBar from "./components/NavBar/NavBar";
import GifAnimation from "./components/Gif/GifAnimation";


const App: React.FC = () => {
    return (
        <div>
            <GlobalStyle/>
            <NavBar/>
            <GifAnimation/>
        </div>
    );
}

export default App;
