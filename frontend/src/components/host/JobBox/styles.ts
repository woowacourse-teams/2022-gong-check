import { css } from '@emotion/react';

import theme from '@/styles/theme';

const jobBox = css`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 56px;
  padding: 12px 16px;
  background-color: ${theme.colors.gray100};
  border-radius: 8px;
  color: ${theme.colors.black};
  font-size: 500;
  font-size: 18px;
`;

const updateButton = css`
  width: auto;
  height: auto;
  padding: 8px 12px;
  font-size: 12px;
  margin: 0 8px;
`;

const deleteButton = css`
  width: auto;
  height: auto;
  padding: 8px 12px;
  font-size: 12px;
  margin: 0;
  background-color: ${theme.colors.red};
`;

const styles = { jobBox, updateButton, deleteButton };

export default styles;
