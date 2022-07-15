import { css } from '@emotion/react';

import theme from '@/styles/theme';

const contentWith = css`
  width: 80%;
  margin: 0 auto;
`;

const spaceInfo = css`
  width: 400px;
  height: 520px;
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
  width: 250px;
  height: 220px;
  border-radius: 8px;
  cursor: pointer;
`;

const notImageBox = css`
  background: ${theme.colors.gray200};
  border: 1px dashed ${theme.colors.black};
  position: relative;
`;

const notImageText = css`
  width: 100%;
  color: ${theme.colors.lightGray};
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
`;

const iconBox = css`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const inputContainer = css`
  border-top: 1px solid ${theme.colors.gray200};
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
  titleWrapper,
  title,
  subTitle,
  imageContainer,
  inputContainer,
  inputWrapper,
  imageWrapper,
  imageBox,
  notImageBox,
  notImageText,
  iconBox,
  input,
};

export default styles;
