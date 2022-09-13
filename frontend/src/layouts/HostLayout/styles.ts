import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = (isManagePath: boolean) => css`
  display: flex;
  flex-direction: column;
  width: 100vw;
  min-height: 100vh;
  height: fit-content;
  background-color: ${theme.colors.white};
  padding-left: ${isManagePath ? '14em' : 0};

  @media screen and (max-width: 1024px) {
    font-size: 14px;
  }
  @media screen and (max-width: 720px) {
    font-size: 12px;
  }
`;

const styles = { layout };

export default styles;
