import { css } from '@emotion/react';

const layout = css`
  padding: 16px 48px;
  display: flex;
  flex-direction: column;
`;

const contents = css`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const cardWrapper = css`
  display: flex;
  gap: 20px;
  margin-bottom: 32px;
`;

const styles = { layout, contents, cardWrapper };

export default styles;
