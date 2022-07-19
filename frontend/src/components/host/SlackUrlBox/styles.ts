import { css } from '@emotion/react';

const jobName = css`
  font-size: 20px;
  line-height: 48px;
  vertical-align: middle;
`;

const input = css`
  margin-left: 8px;
`;

const button = css`
  margin: 0 8px;
  width: 58px;
`;

const slackUrlBox = css`
  display: flex;
  justify-content: center;
  height: 48px;
  margin: 8px 0;
`;

const styles = { slackUrlBox, jobName, input, button };

export default styles;
