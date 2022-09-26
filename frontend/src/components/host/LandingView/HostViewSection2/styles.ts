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
  display: flex;
  justify-content: center;
`;

const title = css`
  position: absolute;
  z-index: 11;
  color: ${theme.colors.gray800};
  animation: ${animation.fadeIn} 1.5s;
  animation-fill-mode: forwards;
  font-size: 3vw;
  top: 2vw;
  left: 50%;
  transform: translateX(-50%);

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 5vw;
  }
`;

const gridWrapper = css`
  display: grid;
  position: absolute;
  animation: ${animation.moveUp} 1.5s;
  animation-fill-mode: forwards;
  z-index: 11;
  gap: 10px 20px;
  grid-template-columns: repeat(2, 1fr);
  top: 20vw;

  & img {
    height: 100%;
    width: 100%;
  }

  @media screen and (max-width: ${screenSize.TABLET}px) {
    grid-template-columns: repeat(1, 1fr);
    row-gap: 20px;
  }
`;

const picture = (color: string) => css`
  box-shadow: 3px 3px 3px 3px ${theme.colors.shadow20};
  border-radius: 4px;
  border: 4px solid ${color};
`;

const styles = { layout, content, title, gridWrapper, picture };

export default styles;
