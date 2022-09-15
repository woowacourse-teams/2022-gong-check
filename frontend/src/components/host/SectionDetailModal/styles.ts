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
  padding: 32px 20px;
  border-radius: 18px;
  position: relative;

  h1 {
    align-self: start;
    font-size: 1.2rem;
    margin: 0 0 8px 32px;
    height: 24px;
  }

  svg {
    position: absolute;
    top: 12px;
    right: 12px;
    cursor: pointer;
  }

  input {
    display: none;
  }
`;

const image = css`
  width: 90%;
  margin-top: 8px;
  cursor: pointer;
  border-radius: 8px;
`;

const description = css`
  width: 90%;
  margin: 20px 0;
  position: relative;

  textarea {
    width: 100%;
    resize: none;
    outline: none;
    border: 2px solid ${theme.colors.shadow20};
    border-radius: 8px;
    font-size: 0.9rem;
    color: ${theme.colors.black};
    padding: 8px;
    ::placeholder {
      font-size: 0.9rem;
      color: ${theme.colors.gray500};
    }
    :focus {
      outline: none;
      ::-webkit-input-placeholder {
        color: transparent;
      }
    }
  }

  span {
    font-size: 12px;
    position: absolute;
    bottom: -14px;
    right: 4px;
  }
`;

const saveButton = (isDisabledButton: boolean) => css`
  width: 90%;
  font-size: 1rem;
  margin: 0;
  background-color: ${isDisabledButton ? theme.colors.gray400 : theme.colors.green};
`;

const styles = { container, image, description, saveButton };

export default styles;
