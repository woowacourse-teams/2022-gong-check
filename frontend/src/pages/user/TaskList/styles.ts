import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  font-size: 16px;
  padding: 0 0 32px 0;
`;

const contents = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  margin-top: 16px;
`;

const location = css`
  margin: 16px 0;
  padding: 24px 16px;
  border: 1px solid ${theme.colors.shadow30};
  border-radius: 24px;
  box-shadow: 2px 2px 2px 0px ${theme.colors.shadow30};
`;

const locationHeader = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 8px;
  min-height: 48px;
`;

const locationName = css`
  font-size: 24px;
  font-weight: 600;
  margin: 0;
`;

const locationHeaderRightItems = css`
  display: flex;
  gap: 10px;
`;

const header = css`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  padding: 4em 0 3em 0;
`;

const thumbnail = (imageUrl: string | undefined) => css`
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

  svg {
    color: ${theme.colors.black};
    cursor: pointer;
  }
`;

const progressBarWrapperSticky = (isSticked: boolean | undefined) => css`
  position: sticky;
  top: 0;
  padding-top: 16px;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${theme.colors.white};
  z-index: 1;

  box-shadow: ${isSticked ? `2px 2px 2px 0px ${theme.colors.shadow30}` : ''};
`;

const progressBarWrapper = css`
  box-shadow: 2px 2px 4px 0px ${theme.colors.shadow40};
  border: 1px solid ${theme.colors.green};
  border-radius: 4px;
  height: 30px;
  width: 80%;
  position: relative;
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

const form = css`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const sectionAllCheckButton = css`
  width: 40px;
  height: 40px;
  margin: 0;
  padding: 0;
  box-shadow: 2px 2px 2px 0px ${theme.colors.shadow30};
`;

const styles = {
  layout,
  contents,
  location,
  locationHeader,
  locationName,
  locationHeaderRightItems,
  arrowBackIconWrapper,
  header,
  thumbnail,
  infoWrapper,
  progressBarWrapperSticky,
  progressBarWrapper,
  progressBar,
  percentText,
  button,
  sectionAllCheckButton,
  form,
};

export default styles;
