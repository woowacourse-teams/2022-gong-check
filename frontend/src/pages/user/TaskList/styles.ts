import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  font-size: 16px;
  padding-bottom: 32px;
`;

const contents = css`
  display: flex;
  flex-direction: column;
  width: 80%;
  align-items: center;
  margin-top: 16px;
`;

const header = css`
  position: relative;
  width: 100%;

  display: flex;
  justify-content: space-evenly;
  align-items: center;
  padding: 4em 0 3em 0;
`;

const headerInfo = css`
  max-width: 420px;
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
`;

const thumbnail = (imageUrl: string) => css`
  width: 120px;
  height: 120px;
  background-image: url(${imageUrl});
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  border-radius: 40%;
  box-shadow: 2px 2px 6px 0px ${theme.colors.shadow60};
`;

const infoWrapper = css`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  p {
    margin: 0;
  }

  p:nth-of-type(2) {
    font-size: 1.8em;
    font-weight: bold;
    margin-top: 0.3em;
  }
`;

const arrowBackIcon = css`
  position: absolute;
  top: 20px;
  left: 20px;

  svg {
    color: ${theme.colors.black};
    cursor: pointer;
  }
`;

const progressBarWrapper = css`
  background-color: ${theme.colors.white};
  box-shadow: 2px 2px 4px 0px ${theme.colors.shadow40};
  border: 1px solid ${theme.colors.green};
  border-radius: 4px;
  height: 30px;
  max-width: 380px;
  width: 80%;
  position: relative;
  padding-top: 16px;
  margin-bottom: 16px;
`;

const progressBar = (percent: number) => css`
  background: ${theme.colors.green};
  height: 100%;
  width: ${percent}%;

  -webkit-animation-duration: 1s;
  animation-duration: 1s;
  -webkit-animation-fill-mode: forwards;
  animation-fill-mode: forwards;
  -webkit-animation-iteration-count: infinite;
  animation-iteration-count: infinite;
  -webkit-animation-name: progress;
  animation-name: progress;
  -webkit-animation-timing-function: linear;
  animation-timing-function: linear;
  -webkit-transition: 0.6s ease;
  transition: 0.6s ease;

  @-webkit-keyframes progress {
    0% {
      background-position: 100% 0;
    }
    100% {
      background-position: -100% 0;
    }
  }

  @keyframes progress {
    0% {
      background-position: 100% 0;
    }
    100% {
      background-position: -100% 0;
    }
  }
`;

const percentText = css`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: ${theme.colors.white};
  text-shadow: -1.5px 0 ${theme.colors.green}, 0 1.5px ${theme.colors.green}, 1.5px 0 ${theme.colors.green},
    0 -1.5px ${theme.colors.green};
  font-weight: bold;
  letter-spacing: 0.5rem;
  font-size: 1.2rem;
`;

const button = (isAllChecked: boolean) =>
  css`
    margin-bottom: 0;
    width: 256px;
    background: ${isAllChecked ? theme.colors.primary : theme.colors.gray400};
  `;

const styles = {
  layout,
  header,
  headerInfo,
  thumbnail,
  arrowBackIcon,
  infoWrapper,
  progressBarWrapper,
  progressBar,
  percentText,
  contents,
  button,
};

export default styles;
