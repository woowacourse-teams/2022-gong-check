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
  font-size: 16px;

  @media screen and (max-width: 720px) {
    font-size: 14px;
  }
`;

const createButton = css`
  margin: 0;
  margin-left: 12px;
  font-size: 1.2em;
  width: fit-content;
  height: fit-content;
  padding: 12px;
  background-color: ${theme.colors.green};
`;

const jobNameInput = css`
  border: none;
  border-radius: 12px;
  width: 55%;
  height: 1.5em;
  padding: 1em;
  font-size: 1.5em;
  font-weight: 500;
  margin: 12px 0;
  background-color: ${theme.colors.gray100};
  outline: 1px solid ${theme.colors.gray400};

  :focus {
    outline: 2px solid ${theme.colors.primary};
  }
`;

const styles = { header, jobNameInput, createButton };

export default styles;
