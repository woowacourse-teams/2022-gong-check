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
  background-color: ${theme.colors.white};
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow20};
  border-radius: 8px;
  position: relative;

  ::-webkit-scrollbar {
    display: none;
  }

  ::-webkit-scrollbar-thumb {
    background: ${theme.colors.shadow20};
    border-radius: 12px;
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
  align-items: center;
  font-size: 20px;
  margin-bottom: 8px;
  padding-bottom: 16px;
  border-bottom: 2px solid ${theme.colors.shadow20};
`;

const deleteButton = css`
  top: 8px;
  right: 8px;
  position: absolute;
  margin: 0 4px;
  color: ${theme.colors.gray800};
  cursor: pointer;
`;

const detailButton = (hasSectionDetailInfo: boolean) => css`
  margin: 0 4px;
  cursor: pointer;
  color: ${hasSectionDetailInfo ? theme.colors.green : theme.colors.gray400};
  :hover {
    animation: ${animation.shake} 2s infinite linear alternate;
  }
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
  font-size: 18px;
  line-height: 38px;
  border: 1px solid ${theme.colors.shadow30};
  border-radius: 12px;
  padding: 0 16px;
  background-color: ${theme.colors.white};
  width: 80%;

  :focus {
    outline: 2px solid ${theme.colors.primary};
  }
`;

const styles = { container, titleWrapper, input, detailButton, deleteButton, newTaskButton };

export default styles;
