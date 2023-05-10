import theme from './theme';
import { css } from '@emotion/react';

const globalStyle = css`
  @font-face {
    font-family: '지마켓';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff') format('woff');
    font-weight: 0;
    font-style: normal;
    font-display: swap;
  }

  * {
    box-sizing: border-box;
    font-family: '지마켓', 'Noto Sans Korean', 'Roboto';
  }

  a {
    text-decoration: none;
    color: ${theme.colors.black};
  }

  body {
    overflow-x: hidden;
    margin: 0;
  }

  button {
    border: none;
    cursor: pointer;
  }

  #root {
    display: flex;
    justify-content: center;
    width: 100vw;
    min-height: 100vh;
    background-color: ${theme.colors.white};
  }

  #modal {
    display: flex;
    justify-content: center;
    position: fixed;
    top: 0;
    z-index: 1;
  }
`;

export default globalStyle;
