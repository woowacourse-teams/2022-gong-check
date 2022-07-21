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

const imageBox = (imageUrl: string | undefined, borderStyle?: string) => css`
  display: block;
  background: ${theme.colors.gray300};
  background-size: cover;
  background-repeat: no-repeat;
  border: 2px ${theme.colors.gray400};
  border-radius: 8px;
  border-style: ${borderStyle};
  width: 15rem;
  height: 15rem;
  position: relative;
  margin: 0 1.5rem;
  background-image: url(${imageUrl});
  cursor: pointer;
`;

const imageInput = css`
  opacity: 0;
  z-index: -1;
`;

const imageCoverText = css`
  width: 100%;
  color: ${theme.colors.gray200};
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
  imageBox,
  imageInput,
  imageCoverText,
  iconBox,
  input,
};

export default styles;
