import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const text404 = css`
  font-size: 5rem;
  margin: 0;
  padding: 0;
`;

const description = css`
  color: ${theme.colors.black};
  font-weight: bold;
  font-size: 1.4rem;
  margin-bottom: 1.8rem;
`;

const mark = css`
  color: ${theme.colors.red};
`;

const content = css`
  position: relative;
  top: 0;
  z-index: 1;
  margin: 20px 0;
`;

const button = css`
  background-color: ${theme.colors.red};
`;

const styles = { layout, text404, description, content, button, mark };

export default styles;
