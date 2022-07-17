import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;

  font-size: 16px;
`;

const contents = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
`;

const location = css`
  margin: 16px;
`;

const locationName = css`
  font-size: 20px;
  font-weight: 600;
  margin: 16px 8px 0;
`;

const header = css`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  padding: 4em 0 3em 0;
`;

const thumbnail = (imageUrl: string) => css`
  width: 120px;
  height: 120px;
  background-image: url(${imageUrl});
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  border-radius: 40%;
`;

const infoWrapper = css`
  p {
    margin: 0;
  }

  p:nth-of-type(1) {
    font-weight: 500;
    font-size: 1em;
  }

  p:nth-of-type(2) {
    font-size: 1.5em;
    font-weight: bold;
    margin-top: 0.5em;
  }
`;

const arrowBackIconWrapper = css`
  position: absolute;
  top: 20px;
  left: 20px;
`;

const progressBarWrapper = css`
  box-shadow: 2px 2px 4px 0px ${theme.colors.shadow40};
  border: 1px solid ${theme.colors.green};
  border-radius: 4px;
  height: 30px;
  width: 80%;
  position: relative;
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

const styles = {
  layout,
  contents,
  location,
  locationName,
  arrowBackIconWrapper,
  header,
  thumbnail,
  infoWrapper,
  progressBarWrapper,
  progressBar,
  percentText,
};

export default styles;
