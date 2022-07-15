import { css } from '@emotion/react';

import theme from '@/styles/theme';

const contentWith = css`
  width: 80%;
  margin: 0 auto;
`;

const button = css`
  width: 5rem;
  height: 2.5rem;
  margin: 0;
  font-size: 1rem;
`;
``;
const spaceInfo = css`
  min-height: 25rem;
  background: ${theme.colors.white};
  border-radius: 8px;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow30};
`;

const titleWrapper = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 1.25rem;
  border-bottom: 1px solid ${theme.colors.gray200};
`;

const title = css`
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

const imageBox = css`
  display: block;
  background: ${theme.colors.gray200};
  background-size: cover;
  background-repeat: no-repeat;
  border: 2px ${theme.colors.gray};
  border-radius: 8px;
  width: 15rem;
  height: 15rem;
  position: relative;
  margin: 0 1.5rem;
  cursor: pointer;
`;

const imageInput = css`
  opacity: 0;
  z-index: -1;
`;

const imageCoverText = css`
  width: 100%;
  color: ${theme.colors.lightGray};
  text-shadow: 0px 0px 4px ${theme.colors.shadow60};
  font-size: 0.8rem;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  z-index: 0;
`;

const iconBox = css`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const inputContainer = css`
  border-top: 1px solid ${theme.colors.gray200};
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
  imageBox,
  imageInput,
  imageCoverText,
  iconBox,
  input,
};

export default styles;
