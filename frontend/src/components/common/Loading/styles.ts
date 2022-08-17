import { css } from '@emotion/react';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = css`
  width: 100vw;
  height: 100vh;
  z-index: 10;
  opacity: 1;
`;

const spinner = css`
  width: 100%;
  height: 100%;
  margin: 0 0 0 -1px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;
  justify-content: center;
  position: relative;
  border: 1px solid rgba(255, 255, 255, 0.3);
`;

const text = css`
  animation: ${animation.shake} 2s 0s infinite;
  font-size: 2rem;
  position: absolute;
  bottom: 40%;
`;

const faceSpinner = css`
  width: 12rem;
  height: 12rem;
  border: 10px solid ${theme.colors.primary};
  border-radius: 4rem;
  display: flex;
  justify-content: center;
  background-color: ${theme.colors.background};
`;

const faceSpinnerEye = css`
  width: 6rem;
  position: relative;
  transform: translate(-50%, 20%);
  animation: ${animation.spinnerFace} 3s infinite cubic-bezier(0.76, 0, 0.24, 1) both;

  &:before,
  &:after {
    content: '';
    left: 0;
    width: 2rem;
    height: 2rem;
    border-radius: 0.8rem;
    position: absolute;
    background-color: ${theme.colors.black};
    animation: ${animation.spinnerEye} 1.5s 1s infinite;
  }
  &:after {
    left: inherit;
    right: 0;
  }
`;

const styles = { layout, spinner, faceSpinner, faceSpinnerEye, text };

export default styles;
