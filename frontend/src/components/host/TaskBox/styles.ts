import { css, keyframes } from '@emotion/react';

import theme from '@/styles/theme';

const shake = keyframes`
  0%, 50%{
    transform: rotate(0deg);
	}
  5%, 15%, 25%, 35%, 45% {
    transform: rotate(13deg);
  }
  10%, 20%, 30%, 40% {
    transform: rotate(-13deg);
  }
`;

const taskBox = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 6px 12px;

  input {
    font-size: 16px;
    width: 70%;
  }

  span {
    margin-right: 8px;
  }
`;

const editButton = css`
  background-color: ${theme.colors.green};
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
    animation: ${shake} 2s infinite linear alternate;
  }
`;

const trash = css`
  margin: 0 4px;
  cursor: pointer;
  color: tomato;
  :hover {
    animation: ${shake} 2s infinite linear alternate;
  }
`;

const styles = { taskBox, editButton, pencil, trash };

export default styles;
