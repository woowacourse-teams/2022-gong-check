import { css } from '@emotion/react';

import animation from '@/styles/animation';
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

const detailButton = css`
  background-color: ${theme.colors.green};
  width: fit-content;
  height: fit-content;
  padding: 8px 12px;
  font-size: 14px;
  margin: 0 4px;
`;

const taskWrapper = css`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 6px 12px;
  height: 40px;

  span {
    font-size: 14px;
    margin-right: 8px;
    cursor: pointer;
  }

  svg {
    margin: 0 4px;
    cursor: pointer;
    color: ${theme.colors.green};
    :hover {
      animation: ${animation.shake} 2s infinite linear alternate;
    }
  }
`;

const styles = {
  titleWrapper,
  detailButton,
  taskWrapper,
};

export default styles;