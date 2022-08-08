import { css } from '@emotion/react';

import theme from '@/styles/theme';

const dimmer = (mode: 'full' | 'mobile') => css`
  background-color: ${theme.colors.shadow80};
  max-width: ${mode === 'full' ? '100vw' : '414px'};
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const styles = { dimmer };

export default styles;
