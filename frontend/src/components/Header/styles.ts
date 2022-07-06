import { css } from '@emotion/react';

import theme from '../../styles/theme';

const header = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 6px 4px -4px ${theme.colors.shadow30};
  width: 100%;
  height: 50px;
  padding: 0 8px;
  position: relative;
`;

const arrowBackIcon = css`
  cursor: pointer;
`;

const styles = { header, arrowBackIcon };

export default styles;
