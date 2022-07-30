import { css } from '@emotion/react';

import theme from '@/styles/theme';

const spaceDeleteButton = css`
  background-color: ${theme.colors.red};
  width: auto;
  height: 2rem;
  font-weight: 500;
  font-size: 1rem;
  padding: 0 12px;
  margin: 0 0 24px 12px;
`;

const styles = { spaceDeleteButton };

export default styles;
