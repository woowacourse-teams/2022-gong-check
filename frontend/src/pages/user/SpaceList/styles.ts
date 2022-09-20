import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px 32px;
`;

const logo = css`
  width: 264px;
  margin: 32px 0;
  aspect-ratio: auto 5 / 1;
`;

const text = css`
  font-weight: 600;
  color: ${theme.colors.gray500};
  margin-bottom: 24px;
`;

const empty = css`
  margin-top: 56px;
  font-size: 24px;
  font-weight: 500;
`;
const styles = { layout, logo, text, empty };

export default styles;
