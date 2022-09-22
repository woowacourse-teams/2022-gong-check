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
`;

const createButton = css`
  margin: 0;
  margin-left: 12px;
  font-size: 1.1em;
  width: fit-content;
  height: fit-content;
  padding: 8px 10px;
  background-color: ${theme.colors.green};
`;

const jobNameInput = css`
  border: none;
  border-radius: 12px;
  width: 64%;
  height: 1.5em;
  padding: 1em;
  font-size: 1.2em;
  font-weight: 500;
  margin: 12px 0;
  background-color: ${theme.colors.white};
  outline: 1px solid ${theme.colors.gray400};

  :focus {
    outline: 2px solid ${theme.colors.primary};
  }
`;

const styles = { header, jobNameInput, createButton };

export default styles;
