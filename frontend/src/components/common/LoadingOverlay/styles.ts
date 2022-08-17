import { css } from '@emotion/react';

import theme from '@/styles/theme';

const loadingOverlay = css`
  top: 0;
  left: 0;
  position: absolute;
  min-width: 100vw;
  height: 100vh;
  background-image: linear-gradient(${theme.colors.shadow50}, ${theme.colors.shadow50});
  z-index: 100;
  cursor: wait;
`;

const styles = {
  loadingOverlay,
};

export default styles;
