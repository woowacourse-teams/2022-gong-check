import { css } from '@emotion/react';

const globalStyle = css`
  * {
    box-sizing: border-box;
  }

  body {
    margin: 0;
    display: flex;
    justify-content: center;
    width: 100vw;
    height: 100vh;
    background-color: #f5f5f5;
  }

  button {
    border: none;
    cursor: pointer;
  }

  #root {
    display: flex;
    flex-direction: column;
    width: 400px;
    min-height: 100vh;
    background-color: white;
    position: absolute;
  }

  #modal {
    position: fixed;
    top: 0;
  }
`;

export default globalStyle;
