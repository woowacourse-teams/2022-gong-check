import { css } from '@emotion/react';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const slideMenu = (isShowMenu: boolean | null) => css`
  position: fixed;
  top: 64px;
  min-height: 100vh;
  background-color: ${theme.colors.white};
  z-index: 10;
  width: 100%;

  animation: ${isShowMenu === true ? animation.navigatorOpen : animation.navigatorClose} 0.2s ease-out;
  animation-fill-mode: forwards;
`;

const styles = { slideMenu };

export default styles;
