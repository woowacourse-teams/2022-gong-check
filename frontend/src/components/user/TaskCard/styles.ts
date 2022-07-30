import { css } from '@emotion/react';

import theme from '@/styles/theme';

const taskCard = css`
  display: flex;
  flex-direction: column;
  width: 320px;
  padding: 16px;
`;

const textWrapper = css`
  display: flex;
  align-items: center;

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

const icon = css`
  color: ${theme.colors.gray500};
  margin-left: 16px;
  cursor: pointer;
`;

const styles = { taskCard, task, icon, textWrapper };

export default styles;
