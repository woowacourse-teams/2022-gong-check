import { css } from '@emotion/react';

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
`;

export default transitions;
