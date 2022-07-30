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
  height: 100px;
  width: 100%;
  border-bottom: 1px solid ${theme.colors.gray300};
  padding: 16px 32px;

  h1 {
    font-size: 28px;
    font-weight: 500;
  }

  button {
    margin: 0;
    margin-left: 12px;
    font-size: 20px;
    width: auto;
    padding: 0 16px;
  }
`;

const grid = css`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(1fr);
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

const styles = { layout, contents, header, grid, createCard, createButton, jobNameInput };

export default styles;
