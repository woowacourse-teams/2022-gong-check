import { css } from '@emotion/react';

const headerDiv = css`
  display: flex;
  justify-content: space-between;
  align-items: center;

  width: 100%;
  height: 50px;

  padding: 0 8px;
`;

const arrowBackIcon = css`
  &:hover {
    cursor: pointer;
  }
`;

const styles = { headerDiv, arrowBackIcon };

export default styles;
