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
  min-width: 356px;
`;

const grid = css`
  display: grid;
  grid-template-columns: repeat(auto-fill, 320px);
  grid-template-rows: repeat(1fr);
  justify-content: center;
  grid-gap: 32px;
  justify-items: center;
  padding: 32px;
`;

const createCard = css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 480px;
  height: 356px;
  background-color: ${theme.colors.gray200};
  font-size: 64px;
  border-radius: 8px;
  cursor: pointer;
  :hover {
    background-color: ${theme.colors.shadow20};
  }
`;

const styles = { layout, contents, grid, createCard };

export default styles;
