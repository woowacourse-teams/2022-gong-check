import { css } from '@emotion/react';

import theme from '@/styles/theme';

const container = css`
  max-width: 400px;
  min-width: 320px;
  width: 80%;
  background-color: ${theme.colors.white};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px 16px;
  border-radius: 18px;
`;

const title = css`
  margin: 0;
`;

const detail = css`
  color: ${theme.colors.gray400};
  margin: 16px 0;
`;

const submitButton = (isDisabledButton: boolean) => css`
  margin-bottom: 0;
  width: 256px;
  background: ${isDisabledButton ? theme.colors.gray400 : theme.colors.primary};
`;

const styles = { title, detail, container, submitButton };

export default styles;
