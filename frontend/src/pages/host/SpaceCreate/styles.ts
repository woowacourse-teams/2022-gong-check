import { css } from '@emotion/react';

const layout = css`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;

  @media screen and (max-width: 1023px) {
    height: calc(100vh - 64px);
  }
`;

const styles = { layout };

export default styles;
