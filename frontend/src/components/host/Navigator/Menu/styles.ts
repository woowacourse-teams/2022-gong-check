import { css } from '@emotion/react';

import theme from '@/styles/theme';

const category = css`
  width: 100%;
  display: flex;
  flex-direction: column;
  margin: 8px 0 12px 0;
`;

const categoryTitle = css`
  font-size: 0.9em;
  font-weight: 600;
  color: ${theme.colors.gray800};
  margin: 8px 0;
  padding: 0 8px;
`;

const categoryList = css`
  width: 100%;
  display: flex;
  flex-direction: column;
`;

const categoryTextWrapper = css`
  display: flex;
  align-items: center;
  width: 100%;
  font-size: 0.8em;
  font-weight: 500;
  background-color: ${theme.colors.gray100};
  padding: 12px 8px;
  margin: 4px 0;
  color: ${theme.colors.gray800};
  cursor: pointer;

  svg {
    margin-bottom: 2px;
  }

  span {
    margin-left: 8px;
  }

  :hover {
    background-color: ${theme.colors.gray200};
  }
`;

const selectedTextWrapper = css`
  background-color: ${theme.colors.skyblue100};

  :hover {
    background-color: ${theme.colors.skyblue200};
  }
`;

const addNewSpace = css`
  display: flex;
  margin: 14px 0;
  justify-content: center;
  width: 100%;
  color: ${theme.colors.gray800};
  font-size: 0.9em;

  span:hover {
    font-weight: 600;
    cursor: pointer;
  }
`;

const styles = {
  category,
  categoryTitle,
  categoryList,
  categoryTextWrapper,
  selectedTextWrapper,
  addNewSpace,
};

export default styles;
