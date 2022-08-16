import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  min-width: 720px;
  height: 70vh;
  display: flex;
  justify-content: center;
  padding: 32px;
`;

const editor = css`
  min-width: 720px;
  height: 480px;
  background-color: whitesmoke;
  display: flex;
  border: 1px solid gray;
`;

const left = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 20%;
  height: 100%;
  background-color: aqua;
  padding: 32px;
  background-color: white;
  border-right: 2px solid gray;
`;

const right = css`
  width: 80%;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

const sectionList = css`
  width: 100%;
  height: 20%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  background-color: whitesmoke;
  border-bottom: 2px solid gray;
`;

const playGround = css`
  width: 100%;
  height: 80%;

  background-size: 10px, 30px, 10px 10px, 30px 30px;
  background-image: linear-gradient(90deg, #00000004 1px, transparent 1px),
    linear-gradient(90deg, #00000004 1px, transparent 1px), linear-gradient(#00000004 1px, transparent 1px),
    linear-gradient(#00000004 1px, transparent 1px);
`;

const styles = {
  layout,
  editor,
  left,
  right,
  sectionList,
  playGround,
};

export default styles;
