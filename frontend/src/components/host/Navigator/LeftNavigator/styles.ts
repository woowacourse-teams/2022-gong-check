import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  font-size: 16px;
  height: 100vh;
  width: 14em;
  position: fixed;
  top: 0;
  left: 0;
  background-color: ${theme.colors.white};
  box-shadow: 6px 0 8px ${theme.colors.gray350};
  z-index: 1;
`;

const logo = css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 200px;
  cursor: pointer;
`;

const logoImage = css`
  width: 160px;
  height: 160px;
`;

const styles = {
  layout,
  logo,
  logoImage,
};

export default styles;
