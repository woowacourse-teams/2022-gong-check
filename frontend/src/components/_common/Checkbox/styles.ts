import { css } from '@emotion/react';

import theme from '@/styles/theme';

const input = css`
  display: none;
  :checked + label {
    background: ${theme.colors.green};
  }
`;

const label = css`
  width: 16px;
  height: 16px;
  border: 1px solid ${theme.colors.black};
  border-radius: 10px;
  cursor: pointer;
`;

const styles = { input, label };

export default styles;
