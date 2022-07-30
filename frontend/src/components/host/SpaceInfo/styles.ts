import { css } from '@emotion/react';

import theme from '@/styles/theme';

const contentWith = css`
  width: 80%;
  margin: 0 auto;
`;

const button = ({ isActive }: { isActive?: boolean }) => css`
  width: 5rem;
  height: 2rem;
  margin: 0;
  font-size: 1rem;
  padding: 8px 0;
  background: ${isActive ? theme.colors.primary : theme.colors.gray400};
`;

const spaceInfo = css`
  height: 100%;
  min-height: 28rem;
  min-width: 21rem;
  background: ${theme.colors.white};
  border-radius: 8px;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow10};
`;

const titleWrapper = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1.25rem;
  border-bottom: 1px solid ${theme.colors.gray300};
`;

const title = css`
  margin: 19px;
  font-size: 1.4rem;
`;

const subTitle = css`
  font-size: 1rem;
  font-weight: bold;
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

const input = css`
  border: none;
  font-size: 1.4rem;
  font-weight: bold;
  cursor: pointer;
  max-width: 12.5rem;

  &:focus {
    outline: none;
  }
`;

const styles = {
  spaceInfo,
  button,
  titleWrapper,
  title,
  subTitle,
  imageContainer,
  inputContainer,
  inputWrapper,
  imageWrapper,
  input,
};

export default styles;
