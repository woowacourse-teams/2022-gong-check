import { css } from '@emotion/react';

import theme from '@/styles/theme';

const container = css`
  max-width: 480px;
  width: 80%;
  background-color: ${theme.colors.white};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px 24px;
  border-radius: 18px;

  div {
    display: flex;
  }
`;

const title = css`
  margin: 0;
  font-size: 1.5rem;
`;

const detail = css`
  color: ${theme.colors.gray400};
  margin: 24px 0;
  font-size: 0.9rem;
`;

const icon = css`
  width: 28px;
  height: 28px;
  margin-right: 12px;
`;

const contents = css`
  display: flex;
  flex-direction: column;
  width: 90%;
  height: 24vh;
  overflow-y: scroll;

  ::-webkit-scrollbar {
    width: 0.3em;
  }

  ::-webkit-scrollbar-thumb {
    background: ${theme.colors.shadow20};
    border-radius: 8px;
  }

  ::-webkit-scrollbar-thumb:hover {
    background: ${theme.colors.shadow60};
  }

  ::-webkit-scrollbar-thumb:active {
    background: ${theme.colors.shadow80};
  }

  ::-webkit-scrollbar-button {
    display: none;
  }
`;

const noJobsInfo = css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;

  font-size: 18px;
  font-weight: 500;
`;

const styles = { title, detail, container, icon, contents, noJobsInfo };

export default styles;
