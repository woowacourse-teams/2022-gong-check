import { css } from '@emotion/react';

import { ScreenModeType } from '@/types';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = css`
  width: 100vw;
  height: 100vh;
`;

const content = css`
  width: 100%;
  height: 100%;
  background-color: ${theme.colors.background};
  position: relative;
`;

const title = (screenMode: ScreenModeType) => css`
  position: absolute;
  top: 2.2vw;
  z-index: 11;
  color: ${theme.colors.primary};
  ${screenMode === 'DESKTOP'
    ? `left: 38.5%; font-size: 4.1vw;`
    : `left: 50%; transform: translateX(-50%); font-size: 6vw;`}
`;

const subTitle = (screenMode: ScreenModeType) => css`
  position: absolute;
  z-index: 11;
  color: ${theme.colors.gray800};
  animation: ${animation.fadeIn} 1.5s;
  animation-fill-mode: forwards;
  ${screenMode === 'DESKTOP'
    ? `top: 10.5vw; left: 40.4%; font-size: 1.3vw;`
    : `top: 16vw; left: 50%; transform: translateX(-50%); font-size: 3vw;`}

  b {
    color: ${theme.colors.green};
  }
`;

const leftSection = (screenMode: ScreenModeType) => css`
  position: absolute;
  z-index: 11;
  animation: ${animation.moveRight} 1.5s;
  animation-fill-mode: forwards;
  ${screenMode === 'DESKTOP' ? `top: 15vw; left: 26%; width: 18%;` : `top: 20%; left: 20%;`}
`;

const rightSection = (screenMode: ScreenModeType) => css`
  position: absolute;
  z-index: 11;
  animation: ${animation.moveLeft} 1.5s;
  animation-fill-mode: forwards;
  ${screenMode === 'DESKTOP' ? `top: 15vw; right: 26%; width: 18%;` : `top: 28%; right: 20%;`}
`;

const styles = {
  layout,
  content,
  title,
  subTitle,
  leftSection,
  rightSection,
};

export default styles;
