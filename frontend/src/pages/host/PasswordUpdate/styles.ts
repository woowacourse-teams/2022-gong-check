import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  svg + span {
    color: ${theme.colors.shadow50};
    font-size: 24px;
    margin: 24px 0 32px 0;
  }
  svg {
    color: ${theme.colors.shadow40};
  }
`;

const content = css`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const inputWrapper = css`
  width: 280px;
  height: 48px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: ${theme.colors.gray200};
  border: 2px solid ${theme.colors.gray400};
  border-radius: 8px;
  padding: 0 12px;

  input {
    height: 80%;
    width: 80%;
    background-color: ${theme.colors.gray200};
    border: none;
    outline: none;
    font-size: 20px;
    color: ${theme.colors.shadow80};
  }

  svg {
    cursor: pointer;
    color: ${theme.colors.shadow70};

    :hover {
      color: ${theme.colors.gray800};
    }
  }
`;

const button = css`
  margin: 0 0 0 8px;
  width: auto;
  padding: 0 16px;
`;

const infoText = css`
  color: ${theme.colors.shadow50};
  margin: 12px 0;
`;

const styles = { layout, content, inputWrapper, button, infoText };

export default styles;
