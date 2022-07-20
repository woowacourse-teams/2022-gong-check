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
  font-size: 18px;
  margin: 20px 0 8px 0;
  font-weight: 600;
  color: ${theme.colors.gray800};
`;

const input = css`
  width: 100%;
`;

const button = css`
  margin: 0 8px;
  width: 58px;
`;

const styles = { slackUrlBox, form, label, input, button };

export default styles;
