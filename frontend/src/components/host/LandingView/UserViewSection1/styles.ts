import { css } from '@emotion/react';

import { ScreenModeType } from '@/types';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = css`
  width: 100vw;
  height: 100vh;
  z-index: 10;
`;

const content = css`
  width: 100%;
  height: 100%;
  background-color: ${theme.colors.background};
  position: relative;
  z-index: 10;
`;

const title = (screenMode: ScreenModeType) => css`
  position: absolute;
  color: ${theme.colors.primary};
  z-index: 11;
  top: 13vw;
  ${screenMode === 'DESKTOP'
    ? `left: 12%; font-size: 4.1vw;`
    : `left: 50%; transform: translateX(-50%); font-size: 6vw;`}
`;

const subTitle = (screenMode: ScreenModeType) => css`
  position: absolute;
  top: 29vw;
  z-index: 11;
  color: ${theme.colors.gray800};
  ${screenMode === 'DESKTOP'
    ? `left: 10%; font-size: 2.7vw;`
    : `left: 30%; transform: translateX(-50%); font-size: 3.7vw;`}
`;

const leftSectionWrapper = css`
  animation: ${animation.moveRight} 1.5s;
  animation-fill-mode: forwards;
  z-index: 11;
  position: absolute;
  height: 100%;
  width: 100%;
`;

const leftSection = (screenMode: ScreenModeType) => css`
  position: absolute;
  ${screenMode === 'DESKTOP' ? `top: 5vw; right: 25%; width: 20%;` : `top: 28%; left: 20%;`}
`;

const leftSectionTitle = (screenMode: ScreenModeType) => css`
  position: absolute;
  top: 29vw;
  animation: ${animation.moveDown} 1.5s;
  animation-fill-mode: forwards;
  color: ${theme.colors.gray800};
  z-index: 11;
  ${screenMode === 'DESKTOP'
    ? `font-size: 2.7vw; left: 24.5%;`
    : `font-size: 3.7vw; left: 42%; transform: translateX(-50%);`}

  b {
    color: ${theme.colors.green};
  }
`;

const rightSectionWrapper = css`
  animation: ${animation.moveLeft} 1.5s;
  animation-fill-mode: forwards;
  z-index: 12;
  position: absolute;
  height: 100%;
  width: 100%;
`;

const rightSection = (screenMode: ScreenModeType) => css`
  position: absolute;
  ${screenMode === 'DESKTOP' ? `top: 12vw; right: 10%; width: 20%;` : `top: 32%; right: 20%;`}
`;

const rightSectionTitle = (screenMode: ScreenModeType) => css`
  position: absolute;
  top: 29vw;
  z-index: 11;
  animation: ${animation.moveUp} 1.5s;
  animation-fill-mode: forwards;
  color: ${theme.colors.gray800};
  ${screenMode === 'DESKTOP'
    ? `font-size: 2.7vw; left: 33%;`
    : `font-size: 3.7vw; left: 54%; transform: translateX(-50%);`}

  b {
    color: ${theme.colors.green};
  }
`;

const styles = {
  layout,
  content,
  title,
  subTitle,
  leftSectionWrapper,
  leftSection,
  leftSectionTitle,
  rightSectionWrapper,
  rightSection,
  rightSectionTitle,
};

export default styles;
