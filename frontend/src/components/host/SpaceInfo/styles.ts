import { css } from '@emotion/react';

import theme from '@/styles/theme';

const contentWith = css`
  padding: 0 24px;
`;

const spaceInfo = css`
  min-width: 320px;
  height: 452px;
  background-color: ${theme.colors.white};
  border-radius: 8px;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow10};

  @media screen and (min-width: 1024px) {
    width: 320px;
  }

  @media screen and (max-width: 1023px) {
    width: 90%;
  }
`;

const titleWrapper = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
  border-bottom: 1px solid ${theme.colors.gray300};
`;

const title = css`
  font-size: 1.2rem;
`;

const subTitle = css`
  font-size: 0.8rem;
`;

const imageContainer = css`
  ${contentWith}
`;

const imageWrapper = css`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding-bottom: 1.25rem;
`;

const inputContainer = css`
  border-top: 1px solid ${theme.colors.gray300};
  padding-bottom: 1.25rem;
`;

const inputWrapper = css`
  ${contentWith}
`;

const nameText = css`
  border: none;
  font-size: 1.2rem;
  font-weight: bold;
  width: 100%;
  margin: 0;
  padding: 0;
`;

const styles = {
  spaceInfo,
  titleWrapper,
  title,
  subTitle,
  imageContainer,
  inputContainer,
  inputWrapper,
  imageWrapper,
  nameText,
};

export default styles;
