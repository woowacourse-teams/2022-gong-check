import { css } from '@emotion/react';

import theme from '@/styles/theme';

const container = css`
  width: 50%;
  min-width: 480px;
  background-color: ${theme.colors.white};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px 16px;
  border-radius: 18px;

  div {
    display: flex;
  }
`;

const title = css`
  margin: 0;
`;

const detail = css`
  color: ${theme.colors.gray400};
  margin: 16px 0;
`;

const icon = css`
  width: 32px;
  height: 32px;
  margin-right: 12px;
`;

const contents = css`
  height: 30vh;
  overflow-y: scroll;

  ::-webkit-scrollbar {
    width: 0.4em;
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

const styles = { title, detail, container, icon, contents };

export default styles;
