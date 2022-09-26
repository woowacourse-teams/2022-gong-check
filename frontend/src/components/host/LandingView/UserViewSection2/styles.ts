import { css } from '@emotion/react';

import screenSize from '@/constants/screenSize';

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

const title = css`
  position: absolute;
  z-index: 11;
  color: ${theme.colors.green};
  width: max-content;
  font-size: 4.1vw;
  top: 13vw;
  right: 10%;

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 6vw;
    top: 10vh;
    left: 50%;
    transform: translateX(-50%);
  }

  @media screen and (max-width: ${screenSize.MOBILE}px) {
    font-size: 7.4vw;
    top: 14vh;
    left: 50%;
    transform: translateX(-50%);
  }
`;

const leftSection = css`
  img,
  source {
    position: absolute;
    animation: ${animation.moveDown} 1.5s;
    animation-fill-mode: forwards;
    z-index: 11;
    width: 20%;
    top: 4vw;
    left: 28%;

    @media screen and (max-width: ${screenSize.TABLET}px) {
      width: 30%;
      top: 38vh;
      left: 16%;
    }

    @media screen and (max-width: ${screenSize.MOBILE}px) {
      width: 30%;
      top: 42vh;
      left: 16%;
    }
  }
`;

const leftSectionTitle = css`
  position: absolute;
  animation: ${animation.moveRight} 1.5s;
  animation-fill-mode: forwards;
  color: ${theme.colors.gray800};
  z-index: 11;
  font-size: 2.7vw;
  top: 29vw;
  right: 28%;

  b {
    color: ${theme.colors.primary};
  }

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 3.7vw;
    top: 24vh;
    left: 27%;
  }

  @media screen and (max-width: ${screenSize.MOBILE}px) {
    font-size: 4.7vw;
    top: 28vh;
    left: 21%;
  }
`;

const rightSection = css`
  img,
  source {
    position: absolute;
    animation: ${animation.moveUp} 1.5s;
    animation-fill-mode: forwards;
    z-index: 12;
    width: 20%;
    top: 7vw;
    left: 6%;

    @media screen and (max-width: ${screenSize.TABLET}px) {
      width: 30%;
      top: 38vh;
      left: 54%;
    }

    @media screen and (max-width: ${screenSize.MOBILE}px) {
      width: 30%;
      top: 42vh;
      left: 54%;
    }
  }
`;

const rightSectionTitle = css`
  position: absolute;
  z-index: 11;
  animation: ${animation.moveLeft} 1.5s;
  animation-fill-mode: forwards;
  color: ${theme.colors.gray800};
  font-size: 2.7vw;
  top: 29vw;
  right: 11%;

  b {
    color: ${theme.colors.primary};
  }
  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 3.7vw;
    top: 24vh;
    left: 50%;
  }

  @media screen and (max-width: ${screenSize.MOBILE}px) {
    font-size: 4.7vw;
    top: 28vh;
    left: 51%;
  }
`;

const styles = {
  layout,
  content,
  title,
  leftSection,
  leftSectionTitle,
  rightSection,
  rightSectionTitle,
};

export default styles;
