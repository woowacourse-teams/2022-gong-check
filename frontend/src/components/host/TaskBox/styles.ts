import { css } from '@emotion/react';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const taskBox = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 6px 12px;
  height: 40px;
  animation: ${animation.fadeIn} 1s;

  span {
    margin-right: 8px;
    cursor: pointer;
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

const pencil = css`
  margin: 0 4px;
  cursor: pointer;
  color: ${theme.colors.primary};
  :hover {
    animation: ${animation.shake} 2s infinite linear alternate;
  }
`;

const trash = css`
  margin: 0 4px;
  cursor: pointer;
  color: ${theme.colors.red};
  :hover {
    animation: ${animation.shake} 2s infinite linear alternate;
  }
`;

const input = css`
  border: 1px solid ${theme.colors.shadow30};
  border-radius: 12px;
  padding: 4px 16px;
  width: 50%;
  font-size: 16px;
  background-color: ${theme.colors.white};

  :focus {
    outline: 2px solid ${theme.colors.primary};
  }
`;

const styles = { taskBox, editButton, pencil, trash, input };

export default styles;
