import { css } from '@emotion/react';

import theme from '@/styles/theme';

const button = css`
  background: ${theme.colors.primary};
  width: 224px;
  height: 48px;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  color: ${theme.colors.white};
  margin: 24px;
`;

const styles = { button };

export default styles;
