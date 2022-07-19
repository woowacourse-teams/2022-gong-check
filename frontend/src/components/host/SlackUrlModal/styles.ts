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

const styles = { title, detail, container, icon };

export default styles;
