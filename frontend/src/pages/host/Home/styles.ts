import { css } from '@emotion/react';

import theme from '@/styles/theme';

const header = css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 64px;
  background-color: ${theme.colors.white};
  box-shadow: 0px 2px 2px 2px ${theme.colors.shadow20};
  z-index: 2;

  img {
    height: 40px;
    transform: translateY(4px);
    aspect-ratio: auto 5 / 1;
  }
`;

const layout = css`
  display: flex;
  padding: 48px 0 64px 0;
  height: calc(100vh - 64px);
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  z-index: 2;

  img {
    width: 80%;
    max-width: 420px;
    aspect-ratio: auto 1 / 1;
  }
`;

const styles = { header, layout };

export default styles;
