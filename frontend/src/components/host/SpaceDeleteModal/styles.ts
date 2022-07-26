import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css``;

const container = css`
  width: 480px;
  background-color: ${theme.colors.white};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 2rem 2.5rem;
  border-radius: 18px;

  div {
    display: flex;
  }
`;

const input = css`
  width: 100%;
  height: 3rem;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  border: 1px ${theme.colors.gray300} solid;
  padding: 24px;

  &:focus {
    outline: none;
  }
`;

const button = (isDisabledButton: boolean) => css`
  width: 100%;
  height: 3rem;
  background: ${isDisabledButton ? theme.colors.gray400 : theme.colors.red};
`;

const textWrapper = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 1rem;
  span {
    margin-bottom: 0.4rem;
  }
  & > span:nth-last-of-type(1) {
    color: ${theme.colors.red};
    font-weight: 600;
  }
`;

const styles = { layout, container, input, button, textWrapper };

export default styles;
