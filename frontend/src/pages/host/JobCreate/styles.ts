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
  background-color: ${theme.colors.white};
`;

const header = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  border-bottom: 1px solid ${theme.colors.gray300};
  padding: 16px 32px;

  h1 {
    font-size: 28px;
    font-weight: 500;
  }

  button {
    margin: 0;
    font-size: 20px;
    width: auto;
    padding: 0 16px;
  }
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
  background-color: ${theme.colors.gray200};
  font-size: 64px;
  cursor: pointer;
  :hover {
    background-color: ${theme.colors.shadow20};
  }
`;

const styles = { layout, contents, header, grid, createCard };

export default styles;
