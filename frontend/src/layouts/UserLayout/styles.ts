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

const styles = { layout };

export default styles;
