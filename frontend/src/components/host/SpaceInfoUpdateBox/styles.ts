import { css } from '@emotion/react';

import theme from '@/styles/theme';

const form = css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 320px;
  padding: 0;
  margin: 0;
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

const button = ({ isActive }: { isActive?: boolean }) => css`
  width: auto;
  height: 2rem;
  margin: 0;
  font-size: 0.9rem;
  padding: 0 12px;
  background: ${isActive ? theme.colors.primary : theme.colors.gray400};
`;

const styles = { form, input, button };

export default styles;
