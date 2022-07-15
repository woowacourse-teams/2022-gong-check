import { css } from '@emotion/react';

const layout = css`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const contents = css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const text = css`
  font-size: 2rem;
  font-weight: bold;
  margin: 0;
`;

const styles = { layout, contents, text };

export default styles;
