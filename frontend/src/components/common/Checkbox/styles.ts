import { css } from '@emotion/react';

import theme from '@/styles/theme';

const input = css`
  display: none;
  :checked + label {
    background: ${theme.colors.green};
  }
`;

const label = css`
  width: 28px;
  height: 28px;
  border: 1px solid ${theme.colors.shadow30};
  border-radius: 10px;
  box-shadow: 2px 2px 2px 0px ${theme.colors.shadow30};
  cursor: pointer;
`;

const styles = { input, label };

export default styles;
