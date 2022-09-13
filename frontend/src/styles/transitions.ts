import { css } from '@emotion/react';

import animation from '@/styles/animation';

const transitions = css`
  .transitions-group {
    width: 100vw;
    overflow-x: hidden;
    position: relative;
    -ms-overflow-style: none;
  }

  .transitions-group::-webkit-scrollbar {
    display: none;
  }
  // slide right
  .slide-right-enter {
    z-index: 1;
    transform: scale(1.1);
    transform: translateX(100%);
  }

  .slide-right-enter.slide-right-enter-active {
    z-index: 1;
    transform: translateX(0);
    transition: transform 500ms ease-in-out;
  }

  .slide-right-exit {
    z-index: 1;
    transform: translateX(0);
  }

  .slide-right-exit.slide-right-exit-active {
    z-index: 1;
    transform: translateX(-100%);
    transition: transform 500ms ease-in-out;
  }

  // slide left
  .slide-left-enter {
    z-index: 1;
    transform: translateX(-100%);
  }

  .slide-left-enter.slide-left-enter-active {
    z-index: 1;
    transform: translateX(0);
    transition: transform 500ms ease-in-out;
  }

  .slide-left-exit {
    z-index: 1;
    transform: translateX(0);
  }

  .slide-left-exit.slide-left-exit-active {
    z-index: 1;
    transform: translateX(100%);
    transition: transform 500ms ease-in-out;
  }

  // right
  .right-enter {
    z-index: 1;
    transform: translate3d(100%, 0, 0);
  }
  .right-enter.right-enter-active {
    z-index: 1;
    transform: translate3d(0, 0, 0);
    transition: transform 600ms;
  }
  .right-exit {
    z-index: 0;
    transform: translate3d(0, 0, 0);
  }
  .right-exit.right-exit-active {
    z-index: 0;
    transform: translate3d(0, 0, 0);
    transition: transform 600ms;
  }

  // left
  .left-enter {
    z-index: 0;
    transform: translate3d(0, 0, 0);
  }
  .left-enter.left-enter-active {
    z-index: 0;
    transform: translate3d(0, 0, 0);
    transition: transform 600ms;
  }
  .left-exit {
    z-index: 1;
    transform: translate3d(0, 0, 0);
  }
  .left-exit.left-exit-active {
    z-index: 1;
    transform: translate3d(100%, 0, 0);
    transition: transform 600ms;
  }

  // image scale up
  .image-scale-up-enter.image-scale-up-enter-active {
    z-index: 1;
    animation: ${animation.scaleUp} 500ms;
  }

  .image-scale-down-exit {
    z-index: 0;
  }

  // image scale down
  .image-scale-up-enter {
    z-index: 0;
  }

  .image-scale-down-exit.image-scale-down-exit-active {
    z-index: 1;
    animation: ${animation.scaleDown} 500ms;
  }
`;

export default transitions;
