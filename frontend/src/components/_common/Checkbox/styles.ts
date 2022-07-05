import { css } from '@emotion/react';

const input = css`
  display: none;
  :checked + label {
    background: #7ed957;
  }
`;

const label = css`
  width: 16px;
  height: 16px;
  border: 1px solid black;
  border-radius: 10px;
  cursor: pointer;
`;

const styles = { input, label };

export default styles;
