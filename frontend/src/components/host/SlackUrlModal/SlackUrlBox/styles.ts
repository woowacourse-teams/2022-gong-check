import { css } from '@emotion/react';

import theme from '@/styles/theme';

const slackUrlBox = css`
  display: flex;
  flex-direction: column;
  width: 100%;
`;

const form = css`
  display: flex;
  justify-content: center;
`;

const label = css`
  font-size: 16px;
  margin: 12px 0 8px 0;
  padding: 0 4px;
  font-weight: 600;
  color: ${theme.colors.gray800};
`;

const input = css`
  width: 100%;
  height: 36px;
`;

const button = css`
  font-size: 0.8rem;
  margin: 0 8px;
  width: 48px;
  height: 32px;
`;

const styles = { slackUrlBox, form, label, input, button };

export default styles;
