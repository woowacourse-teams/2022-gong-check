import { css } from '@emotion/react';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const container = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 480px;
  height: 360px;
  overflow-y: scroll;
  padding: 32px;
  background-color: ${theme.colors.gray200};
  animation: ${animation.moveUp} 2s;

  ::-webkit-scrollbar {
    width: 0.4em;
  }

  ::-webkit-scrollbar-thumb {
    background: ${theme.colors.shadow20};
    border-radius: 8px;
  }

  ::-webkit-scrollbar-thumb:hover {
    background: ${theme.colors.shadow60};
  }

  ::-webkit-scrollbar-thumb:active {
    background: ${theme.colors.shadow80};
  }

  ::-webkit-scrollbar-button {
    display: none;
  }
`;

const titleWrapper = css`
  display: flex;
  justify-content: space-between;
  font-size: 20px;
  margin-bottom: 8px;
  padding-bottom: 16px;
  border-bottom: 1px solid gray;
  height: 56px;

  span {
    font-size: 18px;
    line-height: 38px;
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
  font-size: 16px;
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

  :focus {
    outline: 2px solid ${theme.colors.shadow30};
  }
`;
const styles = { container, titleWrapper, confirmButton, editButton, deleteButton, newTaskButton, input };

export default styles;
