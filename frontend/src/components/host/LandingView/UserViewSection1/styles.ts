import { css } from '@emotion/react';

import screenSize from '@/constants/screenSize';

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

const title = css`
  position: absolute;
  color: ${theme.colors.primary};
  z-index: 11;
  font-size: 4.1vw;
  top: 13vw;
  left: 12%;

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

const subTitle = css`
  position: absolute;
  z-index: 11;
  color: ${theme.colors.gray800};
  font-size: 2.7vw;
  top: 29vw;
  left: 10%;

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 3.7vw;
    top: 24vh;
    left: 36%;
    transform: translateX(-50%);
  }

  @media screen and (max-width: ${screenSize.MOBILE}px) {
    font-size: 4.7vw;
    top: 28vh;
    left: 32%;
    transform: translateX(-50%);
  }
`;

const leftSection = css`
  img,
  source {
    animation: ${animation.moveRight} 1.5s;
    animation-fill-mode: forwards;
    z-index: 11;
    position: absolute;
    width: 20%;
    top: 5vw;
    right: 25%;

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
  animation: ${animation.moveDown} 1.5s;
  animation-fill-mode: forwards;
  color: ${theme.colors.gray800};
  z-index: 11;
  font-size: 2.7vw;
  top: 29vw;
  left: 24.5%;

  b {
    color: ${theme.colors.green};
  }

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 3.7vw;
    top: 24vh;
    left: 48%;
    transform: translateX(-50%);
  }

  @media screen and (max-width: ${screenSize.MOBILE}px) {
    font-size: 4.7vw;
    top: 28vh;
    left: 47%;
    transform: translateX(-50%);
  }
`;

const rightSection = css`
  img,
  source {
    position: absolute;
    animation: ${animation.moveLeft} 1.5s;
    animation-fill-mode: forwards;
    z-index: 12;
    width: 20%;
    top: 12vw;
    right: 10%;

    @media screen and (max-width: ${screenSize.TABLET}px) {
      width: 30%;
      top: 38vh;
      right: 16%;
    }

    @media screen and (max-width: ${screenSize.MOBILE}px) {
      width: 30%;
      top: 42vh;
      right: 16%;
    }
  }
`;

const rightSectionTitle = css`
  position: absolute;
  z-index: 11;
  animation: ${animation.moveUp} 1.5s;
  animation-fill-mode: forwards;
  color: ${theme.colors.gray800};
  font-size: 2.7vw;
  top: 29vw;
  left: 33%;

  b {
    color: ${theme.colors.green};
  }

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 3.7vw;
    top: 24vh;
    left: 60%;
    transform: translateX(-50%);
  }

  @media screen and (max-width: ${screenSize.MOBILE}px) {
    font-size: 4.7vw;
    top: 28vh;
    left: 63%;
    transform: translateX(-50%);
  }
`;

const styles = {
  layout,
  content,
  title,
  subTitle,
  leftSection,
  leftSectionTitle,
  rightSection,
  rightSectionTitle,
};

export default styles;
