import { css } from '@emotion/react';

const taskCard = css`
  display: flex;
  flex-direction: column;
  width: 320px;
  padding: 16px;

  span {
    font-weight: 500;
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

const task = css`
  display: flex;
  gap: 16px;
  margin: 18px 0;
`;

const styles = { taskCard, task };

export default styles;
