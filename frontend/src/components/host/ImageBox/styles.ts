import { css } from '@emotion/react';

import theme from '@/styles/theme';

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

const styles = { imageBox, imageInput, imageCoverText, iconBox };

export default styles;
