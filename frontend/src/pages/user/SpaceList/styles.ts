import { css } from '@emotion/react';

const layout = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  padding: 32px;
`;

const logo = css`
  width: 320px;
  margin-bottom: 48px;
`;

const text = css`
  font-weight: 600;
  color: #84817a;
  margin-bottom: 24px;
`;

const styles = { layout, logo, text };

export default styles;
