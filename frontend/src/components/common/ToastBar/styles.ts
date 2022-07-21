import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = (type: 'SUCCESS' | 'ERROR') => css`
  position: fixed;
  box-shadow: 0px 2px 14px -3px ${theme.colors.black};
  color: ${theme.colors.white};
  background-color: ${type === 'SUCCESS' ? theme.colors.green : theme.colors.red};
  bottom: 20px;
  left: 50%;
  transform: translate(-50%, 0);
  width: 200px;
  min-height: 60px;
  padding: 16px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 10px;
  z-index: 1001;
`;

const text = css`
  display: table-cell;
  text-align: left;
  vertical-align: middle;
`;

const styles = { layout, text };

export default styles;
