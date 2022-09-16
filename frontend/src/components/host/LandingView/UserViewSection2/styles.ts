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
  top: 12vw;
  z-index: 11;
  color: ${theme.colors.green};
  ${screenMode === 'DESKTOP'
    ? `right: 10%; font-size: 4.1vw;`
    : `left: 50%; transform: translateX(-50%); font-size: 6vw;`}
`;

const leftSectionWrapper = css`
  animation: ${animation.moveDown} 1.5s;
  animation-fill-mode: forwards;
  z-index: 11;
  position: absolute;
  height: 100%;
  width: 100%;
`;

const leftSection = (screenMode: ScreenModeType) => css`
  position: absolute;
  ${screenMode === 'DESKTOP' ? `top: 4vw; left: 28%; width: 20%;` : `top: 28%; left: 20%;`}
`;

const leftSectionTitle = (screenMode: ScreenModeType) => css`
  position: absolute;
  top: 28.5vw;
  z-index: 11;
  color: ${theme.colors.gray800};
  animation: ${animation.moveRight} 1.5s;
  animation-fill-mode: forwards;
  ${screenMode === 'DESKTOP' ? `right: 26%; font-size: 2.7vw;` : `right: 38%; font-size: 3.7vw;`}

  b {
    color: ${theme.colors.primary};
  }
`;

const rightSectionWrapper = css`
  position: absolute;
  z-index: 12;
  animation: ${animation.moveUp} 1.5s;
  animation-fill-mode: forwards;
  height: 100%;
  width: 100%;
`;

const rightSection = (screenMode: ScreenModeType) => css`
  position: absolute;
  ${screenMode === 'DESKTOP' ? `top: 7vw; left: 6%; width: 20%;` : `top: 32%; right: 20%;`}
`;

const rightSectionTitle = (screenMode: ScreenModeType) => css`
  position: absolute;
  top: 28.5vw;
  z-index: 11;
  color: ${theme.colors.gray800};
  animation: ${animation.moveLeft} 1.5s;
  animation-fill-mode: forwards;
  ${screenMode === 'DESKTOP' ? `right: 9%; font-size: 2.7vw;` : `right: 13%; font-size: 3.7vw;`}
  b {
    color: ${theme.colors.primary};
  }
`;

const rightSectionLittleTitle = css`
  position: absolute;
  top: 28.5vw;
  right: 17%;
  z-index: 11;
  font-size: 1vw;
  color: ${theme.colors.gray500};
  animation: ${animation.moveLeft} 1.5s;
  animation-fill-mode: forwards;
`;

const styles = {
  layout,
  content,
  title,
  leftSectionWrapper,
  leftSection,
  leftSectionTitle,
  rightSectionWrapper,
  rightSection,
  rightSectionTitle,
  rightSectionLittleTitle,
};

export default styles;
