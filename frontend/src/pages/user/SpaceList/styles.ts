import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px 32px;
`;

const logo = css`
  width: 248px;
  margin: 32px 0;
`;

const text = css`
  font-weight: 600;
  color: ${theme.colors.gray500};
  margin-bottom: 24px;
`;

const styles = { layout, logo, text };

export default styles;
