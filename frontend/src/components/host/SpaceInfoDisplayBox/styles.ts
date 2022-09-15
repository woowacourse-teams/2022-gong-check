import { css } from '@emotion/react';

import theme from '@/styles/theme';

const button = css`
  width: 5rem;
  height: 2rem;
  margin: 0;
  font-size: 0.9rem;
  background: ${theme.colors.primary};
`;

const input = css`
  border: none;
  font-size: 1.2rem;
  font-weight: bold;
  cursor: pointer;
  width: 100%;
  &:focus {
    outline: none;
  }
`;

const styles = { button, input };

export default styles;
