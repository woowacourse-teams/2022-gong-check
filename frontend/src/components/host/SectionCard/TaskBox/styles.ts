import { css } from '@emotion/react';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const taskBox = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  height: 40px;
  animation: ${animation.fadeIn} 1s;

  & > div {
    display: flex;
    align-items: center;
  }
`;

const editButton = css`
  background-color: ${theme.colors.primary};
  width: fit-content;
  height: fit-content;
  padding: 4px 8px;
  font-size: 12px;
  margin: 0;
`;

const deleteButton = css`
  margin-right: 4px;
  cursor: pointer;
  color: ${theme.colors.red};
`;

const input = css`
  border: 1px solid ${theme.colors.shadow30};
  border-radius: 12px;
  padding: 4px 16px;
  width: 70%;
  font-size: 14px;
  background-color: ${theme.colors.gray100};

  :focus {
    outline: 2px solid ${theme.colors.primary};
  }

  ::placeholder {
    font-size: 10px;
  }
`;

const detailButton = (hasSectionDetailInfo: boolean) => css`
  margin: 0 4px;
  cursor: pointer;
  color: ${hasSectionDetailInfo ? theme.colors.green : theme.colors.shadow40};
  :hover {
    animation: ${animation.shake} 2s infinite linear alternate;
  }
`;

const styles = { taskBox, editButton, deleteButton, input, detailButton };

export default styles;
