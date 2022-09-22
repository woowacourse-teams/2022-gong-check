import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  @media screen and (max-width: 389px) {
    font-size: 14px;
  }
  font-size: 16px;
`;
const homeCoverImage = css`
  display: flex;
  justify-content: center;
  align-items: center;

  img {
    width: 22em;
    aspect-ratio: auto 1 / 1;
  }
`;

const textWrapper = css`
  display: flex;
  flex-direction: column;
  align-items: center;

  p {
    margin-bottom: 0;
  }

  p:nth-of-type(1) {
    margin-top: 12px;
    font-weight: bold;
    font-size: 1.4em;
  }
  p:nth-of-type(2) {
    font-size: 1em;
    display: flex;
    flex-direction: column;
    align-items: center;
    color: ${theme.colors.gray300};
    line-height: 1.5rem;
  }
`;

const form = ({ isActiveSubmit }: { isActiveSubmit: boolean }) => css`
  display: flex;
  flex-direction: column;
  margin-top: 1.5rem;
  width: 15em;

  input {
    border: none;
    background-color: ${theme.colors.gray200};
    font-size: 16px;
    &::placeholder {
      color: ${theme.colors.gray400};
    }
    &:focus {
      outline: none;
    }
  }

  input,
  button {
    font-size: 1em;
    width: 100%;
    height: 3em;
    margin: 0.6em 0;
    padding: 1em;
    border-radius: 12px;
  }

  button {
    background: ${isActiveSubmit ? theme.colors.primary : theme.colors.gray400};
  }
`;

const styles = { layout, textWrapper, form, homeCoverImage };

export default styles;
