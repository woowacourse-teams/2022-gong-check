import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  font-size: 16px;
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 14em;
  background-color: ${theme.colors.white};
  box-shadow: 6px 0 8px ${theme.colors.gray350};
  z-index: 1;

  @media screen and (max-width: 1024px) {
    font-size: 14px;
  }
  @media screen and (max-width: 720px) {
    font-size: 12px;
  }
`;

const logo = css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 200px;
`;

const logoImage = css`
  width: 160px;
  height: 160px;
`;

const category = css`
  width: 100%;
  display: flex;
  flex-direction: column;
  margin: 8px 0 24px;
`;

const categoryTitle = css`
  font-size: 1em;
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
  font-size: 0.875em;
  font-weight: 500;
  background-color: ${theme.colors.gray100};
  padding: 12px 8px;
  margin: 4px 0;
  color: ${theme.colors.gray800};
  cursor: pointer;

  svg {
    margin-bottom: 4px;
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
  font-size: 0.875em;

  span:hover {
    font-weight: 600;
    cursor: pointer;
  }
`;

const styles = {
  layout,
  logo,
  logoImage,
  category,
  categoryTitle,
  categoryList,
  categoryTextWrapper,
  selectedTextWrapper,
  addNewSpace,
};

export default styles;
