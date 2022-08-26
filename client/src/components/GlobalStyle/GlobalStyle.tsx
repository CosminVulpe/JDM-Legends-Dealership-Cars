import {createGlobalStyle} from "styled-components/macro";

export const GlobalStyle = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Monserrat', sans-serif;
  }

  html, body {
    overflow-x: hidden;
  }
`;
