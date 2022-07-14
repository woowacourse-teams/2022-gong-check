import { css } from '@emotion/react';

const globalStyle = css`
  * {
    box-sizing: border-box;
  }

  a {
    text-decoration: none;
    color: black;
  }

  // 모달이 웹에서도 모바일 환경 처럼 보일 수 있도록 center 처리
  body {
    margin: 0;
    display: flex;
    justify-content: center;
  }

  button {
    border: none;
    cursor: pointer;
  }

  #root {
    display: flex;
    justify-content: center;
  }

  #modal {
    display: flex;
    justify-content: center;
    position: fixed;
    top: 0;
  }
`;

export default globalStyle;
