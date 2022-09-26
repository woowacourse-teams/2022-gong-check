import { css } from '@emotion/react';

import screenSize from '@/constants/screenSize';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = css`
  width: 100vw;
  height: 100vh;
  overflow: hidden;
`;

const content = css`
  width: 100%;
  height: 100%;
  position: relative;
  background-color: ${theme.colors.background};
`;

const title = css`
  position: absolute;
  z-index: 11;
  white-space: nowrap;
  color: ${theme.colors.gray800};
  font-size: 3vw;
  top: 20%;
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

const subTitle = css`
  position: absolute;
  z-index: 11;
  color: ${theme.colors.gray800};
  animation: ${animation.fadeIn} 1.5s;
  white-space: nowrap;
  font-size: 4vw;
  top: 60%;
  left: 50%;
  transform: translateX(-50%);

  b {
    color: ${theme.colors.primary};
  }

  b + b {
    color: ${theme.colors.green};
  }

  @media screen and (max-width: ${screenSize.TABLET}px) {
    font-size: 6vw;
  }
`;

const styles = { layout, content, title, subTitle };

export default styles;
