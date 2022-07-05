import { css } from '@emotion/react';

const taskCard = css`
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  width: 320px;
  border-radius: 20px;
  padding: 24px;
  box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.3);
`;

const task = css`
  display: flex;
  gap: 16px;
  margin: 18px 0;
`;

const styles = { taskCard, task };

export default styles;
