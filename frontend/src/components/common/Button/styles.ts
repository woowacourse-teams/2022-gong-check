import { css } from '@emotion/react';

import theme from '@/styles/theme';

const button = css`
  background: ${theme.colors.primary};
  width: auto;
  height: 48px;
  border-radius: 12px;
  font-size: 14px;
  font-weight: 600;
  color: ${theme.colors.white};
  margin: 24px;
`;

const styles = { button };

export default styles;
