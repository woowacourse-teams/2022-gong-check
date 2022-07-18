import { css } from '@emotion/react';

const transitions = css`
  .transitions-group {
    width: 400px;
    overflow-x: hidden;
    position: relative;
  }

  // slide right
  .slide-right-enter {
    transform: scale(1.1);
    transform: translateX(100%);
  }

  .slide-right-enter.slide-right-enter-active {
    transform: translateX(0);
    transition: transform 500ms ease-in-out;
  }

  .slide-right-exit {
    transform: translateX(0);
  }

  .slide-right-exit.slide-right-exit-active {
    transform: translateX(-100%);
    transition: transform 500ms ease-in-out;
  }

  // slide left
  .slide-left-enter {
    transform: translateX(-100%);
  }

  .slide-left-enter.slide-left-enter-active {
    transform: translateX(0);
    transition: transform 500ms ease-in-out;
  }

  .slide-left-exit {
    transform: translateX(0);
  }

  .slide-left-exit.slide-left-exit-active {
    transform: translateX(100%);
    transition: transform 500ms ease-in-out;
  }

  // right
  .right-enter {
    z-index: 1;
    transform: translate3d(100%, 0, 0);
  }
  .right-enter.right-enter-active {
    transform: translate3d(0, 0, 0);
    transition: all 500ms;
  }

  .right-exit {
    transform: translate3d(0, 0, 0);
  }

  .right-exit.right-exit-active {
    transform: translate3d(0, 0, 0);
    transition: all 700ms;
  }

  // left
  .left-enter {
    transform: translate3d(0, 0, 0);
  }
  .left-enter.left-enter-active {
    transform: translate3d(0, 0, 0);
    transition: all 500ms;
  }

  .left-exit {
    z-index: 1;
    transform: translate3d(0, 0, 0);
  }

  .left-exit.left-exit-active {
    transform: translate3d(100%, 0, 0);
    transition: all 700ms;
  }
`;

export default transitions;
