import { css } from '@emotion/react';

import theme from '@/styles/theme';

const input = css`
  border: none;
  background-color: ${theme.colors.gray200};
  width: 100%;
  border-radius: 12px;
  width: 256px;
  height: 48px;
  padding: 8px 16px;
  font-size: 16px;
  &::placeholder {
    color: ${theme.colors.gray400};
  }
  &:focus {
    outline: none;
  }
`;

const styles = { input };

export default styles;
