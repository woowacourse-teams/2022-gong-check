import { css } from '@emotion/react';

import theme from '@/styles/theme';

const container = (isLoadingImage: boolean) => css`
  width: 560px;
  background-color: ${theme.colors.white};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 32px 20px;
  border-radius: 18px;
  position: relative;
  cursor: ${isLoadingImage ? 'wait' : 'pointer'};

  h1 {
    align-self: start;
    font-size: 24px;
    margin: 0 0 16px 32px;
  }

  svg {
    position: absolute;
    top: 12px;
    right: 12px;
    cursor: ${isLoadingImage ? 'wait' : 'pointer'};
  }

  input {
    display: none;
  }
`;

const image = (isLoadingImage: boolean) => css`
  width: 400px;
  height: 250px;
  cursor: ${isLoadingImage ? 'wait' : 'pointer'};
`;

const description = (isLoadingImage: boolean) => css`
  width: 90%;
  margin: 20px 0;
  position: relative;

  textarea {
    width: 100%;
    resize: none;
    outline: none;
    border: 2px solid ${theme.colors.shadow20};
    border-radius: 8px;
    font-size: 16px;
    color: ${theme.colors.black};
    padding: 8px;
    ${isLoadingImage && 'cursor: wait'};
    ::placeholder {
      font-size: 16px;
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

const saveButton = (isDisabledButton: boolean, isLoadingImage: boolean) => css`
  font-size: 20px;
  margin: 0;
  background-color: ${isDisabledButton ? theme.colors.gray400 : theme.colors.green};
  cursor: ${isLoadingImage ? 'wait' : 'pointer'};
`;

const styles = { container, image, description, saveButton };

export default styles;
