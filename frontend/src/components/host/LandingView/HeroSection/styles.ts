import { css } from '@emotion/react';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = (isFull: boolean) => css`
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  ${isFull ? `transform: translateY(68px);` : `position: fixed; top: 0; z-index: 100;`};
`;

const content = css`
  height: 100vh;
  position: relative;
  top: 0;
  width: 100%;
  z-index: 1;
  background-color: ${theme.colors.background};
`;

const homeCoverWrapper = css`
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
`;

const homeCover = css`
  margin: 1em 0;
  animation: ${animation.wave} 2s alternate linear infinite;
`;

const arrowDownWrapper = css`
  position: absolute;
  bottom: 20px;
  color: ${theme.colors.primary};
  width: 100%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  z-index: 3;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  animation: ${animation.moveDown} 2s 0s infinite;
`;

const canvas = css`
  height: 100%;
  left: 50%;
  position: relative;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  width: 100%;
  z-index: 2;
  opacity: 0.85;
`;

const bottomWrapper = (isFull: boolean) => css`
  margin-bottom: ${isFull ? '20vh' : '100vh'};
  background-color: ${theme.colors.background};
`;

const styles = { layout, content, homeCoverWrapper, homeCover, arrowDownWrapper, canvas, bottomWrapper };

export default styles;
