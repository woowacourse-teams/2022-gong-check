import { css } from '@emotion/react';

import { ScreenModeType } from '@/types';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = css`
  width: 100vw;
  height: 42vh;
`;

const content = css`
  width: 100%;
  height: 100%;
  background-color: ${theme.colors.background};
  display: flex;
  align-items: center;
  justify-content: center;
`;

const title = (screenMode: ScreenModeType) => css`
  z-index: 11;
  font-size: ${screenMode === 'DESKTOP' ? `2.7vw` : `5vw`};
  color: ${theme.colors.gray800};
  animation: ${animation.moveDown} 1.5s;
  animation-fill-mode: forwards;
  padding-bottom: 8vh;
`;

const styles = { layout, content, title };

export default styles;
