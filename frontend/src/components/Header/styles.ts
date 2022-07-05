import { css } from '@emotion/react';

const header = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
  box-shadow: 0 6px 4px -4px rgba(0, 0, 0, 0.3);
  width: 100%;
  height: 50px;

  padding: 0 8px;
`;

const arrowBackIcon = css`
  &:hover {
    cursor: pointer;
  }
`;

const styles = { header, arrowBackIcon };

export default styles;
