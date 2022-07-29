import { css } from '@emotion/react';

import theme from '@/styles/theme';

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
    cursor: pointer;
  }

  button {
    margin: 0;
    margin-left: 12px;
    font-size: 20px;
    width: auto;
    padding: 0 16px;
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
  background-color: ${theme.colors.white};

  :focus {
    outline: 2px solid ${theme.colors.primary};
  }
`;

const styles = { header, jobNameInput, createButton };

export default styles;
