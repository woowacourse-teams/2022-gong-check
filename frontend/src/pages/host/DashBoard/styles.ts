import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;

  @media screen and (min-width: 1024px) {
    padding: 32px 48px;
  }

  @media screen and (max-width: 1023px) {
    padding: 16px;
  }
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
  gap: 20px;
  margin-bottom: 20px;

  @media screen and (min-width: 1024px) {
    flex-direction: row;
    justify-content: space-around;
  }

  @media screen and (max-width: 1023px) {
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
`;

const linkButton = css`
  width: auto;
  height: 2rem;
  padding: 0 12px;
  font-size: 0.9rem;
  margin: 0 0 16px 12px;
  background-color: ${theme.colors.green};
  color: ${theme.colors.white};
  box-shadow: 0px 0px 1px 1px ${theme.colors.shadow10};

  svg {
    height: 20px;
    width: 20px;
    transform: translateY(3px);
  }

  @media screen and (min-width: 1024px) {
    svg {
      height: 16px;
      width: 16px;
      margin-right: 4px;
    }
  }

  @media screen and (max-width: 1023px) {
    span {
      display: none;
    }
  }
`;

const slackButton = css`
  width: auto;
  height: 2rem;
  padding: 0 12px;
  font-size: 0.9rem;
  margin: 0 0 16px 12px;
  background-color: ${theme.colors.white};
  color: ${theme.colors.black};
  box-shadow: 0px 0px 1px 1px ${theme.colors.shadow30};

  img {
    height: 18px;
    width: 18px;
    transform: translateY(2px);
  }

  @media screen and (min-width: 1024px) {
    img {
      height: 14px;
      width: 14px;
      margin-right: 6px;
    }
  }

  @media screen and (max-width: 1023px) {
    span {
      display: none;
    }
  }
`;

const buttons = css`
  display: flex;
  justify-content: end;

  @media screen and (min-width: 1024px) {
    width: 100%;
  }

  @media screen and (max-width: 1023px) {
    width: 90%;
  }
`;

const styles = { layout, contents, cardWrapper, slackButton, linkButton, buttons };

export default styles;
