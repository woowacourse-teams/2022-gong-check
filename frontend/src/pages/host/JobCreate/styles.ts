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
  min-width: 720px;
  background-color: ${theme.colors.white};
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
  height: 360px;
  background-color: ${theme.colors.gray200};
  font-size: 64px;
  border-radius: 8px;
  cursor: pointer;
  :hover {
    background-color: ${theme.colors.shadow20};
  }
`;

const createButton = css`
  background-color: ${theme.colors.green};
`;

const jobNameInput = css`
  border: none;
  border-radius: 12px;
  width: 50%;
  height: 48px;
  padding: 8px 16px;
  font-size: 28px;
  font-weight: 500;
  margin: 12px 0;
  background-color: ${theme.colors.shadow20};

  :focus {
    outline: 2px solid ${theme.colors.shadow30};
  }
`;

const styles = { layout, contents, grid, createCard, createButton, jobNameInput };

export default styles;
