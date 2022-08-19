import { css } from '@emotion/react';

import theme from '@/styles/theme';

const button = ({ isActive }: { isActive?: boolean }) => css`
  width: 5rem;
  height: 2rem;
  margin: 0;
  font-size: 1rem;
  padding: 8px 0;
  background: ${isActive ? theme.colors.primary : theme.colors.gray400};
`;

const input = css`
  border: none;
  font-size: 1.4rem;
  font-weight: bold;
  cursor: pointer;

  width: 100%;
  &:focus {
    outline: none;
  }
`;

const styles = {
  button,
  input,
};

export default styles;
