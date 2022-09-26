import { css } from '@emotion/react';

import { ScreenModeType } from '@/types';

import screenSize from '@/constants/screenSize';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = (screenMode: ScreenModeType) =>
  css`
    width: 100vw;
    height: 100vh;

    @media screen and (max-width: ${screenSize.TABLET}px) {
      height: 50vh;
    }
  `;

const content = css`
  width: 100%;
  height: 100%;
  position: relative;
  background-color: ${theme.colors.background};
`;

const title = (screenMode: ScreenModeType) => css`
  position: absolute;
  z-index: 11;
  white-space: nowrap;
  color: ${theme.colors.gray800};
  font-size: 2.7vw;
  top: 21.4vw;
  left: 50%;
  transform: translateX(-50%);

  b {
    color: ${theme.colors.primary};
  }

  b + b {
    color: ${theme.colors.green};
  }

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 4vw;
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
