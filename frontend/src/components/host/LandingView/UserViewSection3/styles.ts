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
  color: ${theme.colors.primary};
  font-size: 4.1vw;
  top: 2.2vw;
  left: 38.5%;

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
  animation: ${animation.fadeIn} 1.5s;
  animation-fill-mode: forwards;
  color: ${theme.colors.gray800};
  font-size: 1.3vw;
  width: max-content;
  top: 10.5vw;
  left: 40.4%;

  b {
    color: ${theme.colors.green};
  }

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 3.7vw;
    top: 24vh;
    left: 50%;
    transform: translateX(-50%);
  }

  @media screen and (max-width: ${screenSize.MOBILE}px) {
    font-size: 4.7vw;
    top: 28vh;
    left: 50%;
    transform: translateX(-50%);
  }
`;

const leftSection = css`
  img,
  source {
    position: absolute;
    animation: ${animation.moveRight} 1.5s;
    animation-fill-mode: forwards;
    z-index: 11;
    width: 18%;
    top: 15vw;
    left: 26%;

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

const rightSection = css`
  img,
  source {
    position: absolute;
    animation: ${animation.moveLeft} 1.5s;
    animation-fill-mode: forwards;
    z-index: 11;
    width: 18%;
    top: 15vw;
    right: 26%;

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

const styles = {
  layout,
  content,
  title,
  subTitle,
  leftSection,
  rightSection,
};

export default styles;
