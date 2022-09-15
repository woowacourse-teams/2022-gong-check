import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  font-size: 16px;
  position: fixed;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;

  background-color: ${theme.colors.white};
  z-index: 1;

  width: 100vw;
  height: 64px;
  box-shadow: 0 2px 2px 0px ${theme.colors.shadow30};

  svg {
    color: ${theme.colors.gray800};
    cursor: pointer;
    position: absolute;
    left: 12px;
  }
`;

const logo = css`
  height: 40px;
  transform: translateY(4px);
  cursor: pointer;
`;

const styles = {
  layout,
  logo,
};

export default styles;
