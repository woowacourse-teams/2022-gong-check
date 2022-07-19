import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 32px;
`;

const contents = css`
  width: 95%;
  min-height: 100%;
  background-color: white;
`;

const pageTitle = css`
  width: 100%;
  border-bottom: 1px solid ${theme.colors.gray200};
  padding: 8px 32px;
  font-size: 28px;
`;

const grid = css`
  display: grid;
  grid-template-columns: repeat(2, calc(100vw / 3));
  grid-template-rows: repeat(1fr);
  grid-gap: 32px;
  justify-items: center;
  padding: 32px;
`;

const createCard = css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 480px;
  height: 360px;
  background-color: ${theme.colors.lightGray};
  font-size: 64px;
  cursor: pointer;
  :hover {
    background-color: ${theme.colors.shadow20};
  }
`;

const styles = { layout, contents, pageTitle, grid, createCard };

export default styles;
