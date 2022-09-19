import { css } from '@emotion/react';

import { ScreenModeType } from '@/types';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = css`
  margin-top: 10vh;
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

const title = (screenMode: ScreenModeType) => css`
  position: absolute;
  top: 2vw;
  left: 50%;
  transform: translateX(-50%);
  z-index: 11;
  font-size: ${screenMode === 'DESKTOP' ? `2.7vw` : `5vw`};
  color: ${theme.colors.gray800};
  animation: ${animation.fadeIn} 1.5s;
  animation-fill-mode: forwards;
`;

const gridWrapper = css`
  display: grid;
  position: absolute;
  top: 14vw;
  animation: ${animation.moveUp} 1.5s;
  animation-fill-mode: forwards;
  z-index: 11;
  gap: 10px 20px;
  grid-template-columns: repeat(2, 1fr);

  @media (max-width: 768px) {
    grid-template-columns: repeat(1, 1fr);
    row-gap: 20px;
  }

  & img {
    height: 100%;
    width: 100%;
  }
`;

const picture = (color: string) => css`
  box-shadow: 3px 3px 3px 3px ${theme.colors.shadow20};
  border-radius: 4px;
  border: 4px solid ${color};
`;

const styles = { layout, content, title, gridWrapper, picture };

export default styles;
