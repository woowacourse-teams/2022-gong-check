import { css } from '@emotion/react';

import theme from '@/styles/theme';

const titleWrapper = css`
  display: flex;
  justify-content: space-between;
  font-size: 20px;
  margin-bottom: 8px;
  padding-bottom: 16px;
  border-bottom: 2px solid ${theme.colors.shadow20};
  height: 56px;

  span {
    font-size: 18px;
    line-height: 38px;
    cursor: pointer;
  }
`;

const confirmButton = css`
  background-color: ${theme.colors.primary};
  width: fit-content;
  height: fit-content;
  padding: 8px 12px;
  font-size: 14px;
  margin: 0 8px;
`;

const editButton = css`
  background-color: ${theme.colors.primary};
  width: fit-content;
  height: fit-content;
  padding: 8px 12px;
  font-size: 14px;
  margin: 0 4px;
`;

const deleteButton = css`
  background-color: ${theme.colors.red};
  width: fit-content;
  height: fit-content;
  padding: 8px 12px;
  font-size: 14px;
  margin: 0 4px;
`;

const newTaskButton = css`
  align-self: center;
  margin: 8px 0;
  font-size: 12px;
  padding: 8px 16px;
  border-radius: 24px;
  background-color: ${theme.colors.shadow10};
  :hover {
    background-color: ${theme.colors.shadow20};
  }
`;

const input = css`
  border: 1px solid ${theme.colors.shadow30};
  border-radius: 12px;
  padding: 0 16px;
  font-size: 18px;
  background-color: ${theme.colors.white};
  width: 50%;

  :focus {
    outline: 2px solid ${theme.colors.primary};
  }
`;

const styles = {
  titleWrapper,
  input,
  confirmButton,
  editButton,
  deleteButton,
  newTaskButton,
};

export default styles;
