import { css } from '@emotion/react';

import theme from '@/styles/theme';

const contentWith = css`
  width: 80%;
  margin: 0 auto;
`;

const button = css`
  width: 80px;
  height: 40px;
  margin: 0;
`;

const spaceInfo = css`
  width: 400px;
  min-height: 500px;
  background: ${theme.colors.white};
  border-radius: 8px;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow30};
`;

const titleWrapper = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  border-bottom: 1px solid ${theme.colors.gray200};
`;

const title = css`
  font-size: 24px;
`;

const subTitle = css`
  font-size: 18px;
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
  padding-bottom: 20px;
`;

const imageBox = css`
  display: block;
  background: ${theme.colors.gray200};
  background-size: cover;
  background-repeat: no-repeat;
  border: 2px ${theme.colors.gray};
  border-radius: 8px;
  width: 300px;
  height: 300px;
  position: relative;
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
  font-size: 14px;
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
  padding-bottom: 20px;
`;

const inputWrapper = css`
  ${contentWith}
`;

const input = css`
  border: none;
  font-size: 30px;
  font-weight: bold;
  cursor: pointer;
  max-width: 280px;

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
