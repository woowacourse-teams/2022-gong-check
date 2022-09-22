import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css``;

const container = css`
  max-width: 480px;
  width: 80%;
  background-color: ${theme.colors.white};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 2rem;
  border-radius: 18px;

  div {
    display: flex;
  }
`;

const input = css`
  width: 100%;
  height: 2rem;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  border: 1px ${theme.colors.gray300} solid;
  padding: 16px;

  &:focus {
    outline: none;
  }
`;

const button = (isDisabledButton: boolean) => css`
  width: 100%;
  height: 2.4rem;
  background: ${isDisabledButton ? theme.colors.gray400 : theme.colors.red};
  margin: 16px 0;
`;

const textWrapper = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 0.8rem;
  font-size: 0.9rem;
  color: ${theme.colors.gray500};
  span {
    margin-bottom: 0.8rem;
  }
  & > span:nth-last-of-type(1) {
    color: ${theme.colors.red};
    font-size: 1.1rem;
    font-weight: 600;
  }
`;

const alert = css`
  font-size: 0.8rem;
  color: ${theme.colors.gray500};
`;

const styles = { layout, container, input, button, textWrapper, alert };

export default styles;
