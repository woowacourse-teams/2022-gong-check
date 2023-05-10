import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const content = css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-bottom: 40px;
`;

const inputWrapper = css`
  width: 280px;
  height: 36px;
  display: flex;
  justify-content: flex;
  align-items: center;
  background-color: ${theme.colors.white};
  border: 1px solid ${theme.colors.gray400};
  border-radius: 8px;
  padding: 0 12px;

  input {
    height: 80%;
    width: 80%;
    background-color: ${theme.colors.white};
    border: none;
    outline: none;
    font-size: 16px;

    &::placeholder {
      color: ${theme.colors.gray400};
    }
  }

  svg {
    margin-left: auto;
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
  height: 36px;
  padding: 0 16px;
`;

const infoText = css`
  color: ${theme.colors.shadow50};
  margin: 12px 0;
`;

const label = css`
  width: 360px;
  display: flex;
  justify-content: start;
  align-items: center;
  color: ${theme.colors.gray800};
  font-size: 18px;
  margin-bottom: 8px;

  svg {
    margin-right: 4px;
  }
`;

const flex = css`
  display: flex;
`;

const styles = { layout, content, inputWrapper, button, infoText, label, flex };

export default styles;
