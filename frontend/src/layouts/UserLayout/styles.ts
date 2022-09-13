import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: 100%;
  position: absolute;
  background-color: ${theme.colors.white};
`;

const fallback = css`
  opacity: 1;
`;

const styles = { layout, fallback };

export default styles;
