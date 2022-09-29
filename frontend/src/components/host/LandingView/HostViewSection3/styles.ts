import { css } from '@emotion/react';

import screenSize from '@/constants/screenSize';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = css`
  width: 100vw;
  height: 60vh;
  overflow: hidden;
`;

const content = css`
  width: 100%;
  height: 100%;
  background-color: ${theme.colors.background};
  display: flex;
  align-items: center;
  justify-content: center;
`;

const title = css`
  z-index: 11;
  color: ${theme.colors.gray800};
  animation: ${animation.moveDown} 1.5s;
  animation-fill-mode: forwards;
  padding-bottom: 8vh;
  font-size: 3vw;

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 5vw;
  }
`;

const styles = { layout, content, title };

export default styles;
