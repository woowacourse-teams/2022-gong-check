import theme from './theme';
import { css } from '@emotion/react';

const globalStyle = css`
  * {
    box-sizing: border-box;
  }

  a {
    text-decoration: none;
    color: ${theme.colors.black};
  }

  // 모달이 웹에서도 모바일 환경 처럼 보일 수 있도록 center 처리
  body {
    margin: 0;
    display: flex;
    justify-content: center;
    overflow-x: hidden;
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
    background-color: ${theme.colors.gray350};
  }

  #modal {
    display: flex;
    justify-content: center;
    position: fixed;
    top: 0;
  }
`;

export default globalStyle;
