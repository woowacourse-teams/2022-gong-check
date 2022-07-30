import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  padding: 32px 48px;
  display: flex;
  flex-direction: column;
`;

const contents = css`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const cardWrapper = css`
  display: flex;
  width: 100%;
  justify-content: space-around;
  gap: 20px;
  margin-bottom: 32px;
`;

const linkButton = css`
  width: auto;
  height: 2rem;
  padding: 0 12px;
  font-weight: 500;
  font-size: 1rem;
  margin: 0 0 16px 12px;
  background-color: ${theme.colors.green};
  color: ${theme.colors.white};
  box-shadow: 0px 0px 1px 1px ${theme.colors.shadow10};

  svg {
    height: 14px;
    width: 14px;
    margin-right: 6px;
    transform: translateY(2px);
  }
`;

const slackButton = css`
  width: auto;
  height: 2rem;
  padding: 0 12px;
  font-weight: 500;
  font-size: 1rem;
  margin: 0 0 16px 12px;
  background-color: ${theme.colors.white};
  color: ${theme.colors.black};
  box-shadow: 0px 0px 1px 1px ${theme.colors.shadow30};

  img {
    height: 14px;
    width: 14px;
    margin-right: 6px;
    transform: translateY(2px);
  }
`;

const buttons = css`
  width: 100%;
  display: flex;
  justify-content: end;
`;

const styles = { layout, contents, cardWrapper, slackButton, linkButton, buttons };

export default styles;
