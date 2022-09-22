import { css } from '@emotion/react';

import { ScreenModeType } from '@/types';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = (screenMode: ScreenModeType) =>
  css`
    width: 100vw;
    height: ${screenMode === 'DESKTOP' ? `100vh` : `50vh`};
  `;

const content = css`
  width: 100%;
  height: 100%;
  position: relative;
  background-color: ${theme.colors.background};
`;

const title = (screenMode: ScreenModeType) => css`
  top: 21.4vw;
  left: 50%;
  transform: translateX(-50%);
  z-index: 11;
  font-size: ${screenMode === 'DESKTOP' ? `2.7vw` : `5vw`};
  white-space: nowrap;
  color: ${theme.colors.gray800};
  position: absolute;
  b {
    color: ${theme.colors.primary};
  }

  b + b {
    color: ${theme.colors.green};
  }
`;

const subTitle = (screenMode: ScreenModeType) => css`
  position: absolute;
  top: 44vw;
  z-index: 11;
  color: ${theme.colors.gray800};
  animation: ${animation.fadeIn} 1.5s;
  white-space: nowrap;

  ${screenMode === 'DESKTOP'
    ? `left: 33%; font-size: 2.7vw;`
    : `left: 50%; transform: translateX(-50%); font-size: 5vw;`}

  b {
    color: ${theme.colors.primary};
  }

  b + b {
    color: ${theme.colors.green};
  }
`;

const styles = { layout, content, title, subTitle };

export default styles;
