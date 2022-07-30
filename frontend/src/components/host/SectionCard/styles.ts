import { css } from '@emotion/react';

import theme from '@/styles/theme';

const container = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 480px;
  height: 360px;
  overflow-y: scroll;
  padding: 16px 32px;
  background-color: ${theme.colors.white};
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow20};
  border-radius: 8px;
  ::-webkit-scrollbar {
    width: 0.4em;
  }

  ::-webkit-scrollbar-thumb {
    background: ${theme.colors.shadow20};
    border-radius: 12px;
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

const switchWrapper = css`
  display: flex;
  justify-content: center;
  width: 100%;
  margin-bottom: 24px;
`;

const styles = {
  container,
  switchWrapper,
};

export default styles;
