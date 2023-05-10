import { css } from '@emotion/react';

import theme from '@/styles/theme';

const sticky = (isActiveSticky: boolean) => css`
  position: sticky;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 16px;
  background-color: ${theme.colors.white};
  z-index: 1;

  box-shadow: ${isActiveSticky ? `2px 2px 2px 0px ${theme.colors.shadow30}` : ''};
`;

const styles = {
  sticky,
};

export default styles;
