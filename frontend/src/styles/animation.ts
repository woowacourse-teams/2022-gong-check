import { keyframes } from '@emotion/react';

const fadeIn = keyframes`
  from {   
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const fadeOut = keyframes`
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
`;

const shake = keyframes`
  0%, 50%{
    transform: rotate(0deg);
	}
  5%, 15%, 25%, 35%, 45% {
    transform: rotate(13deg);
  }
  10%, 20%, 30%, 40% {
    transform: rotate(-13deg);
  }
`;

const littleShake = keyframes`
  0%, 50%{
    transform: rotate(0deg);
	}
  5%, 15%, 25%, 35%, 45% {
    transform: rotate(5deg);
  }
  10%, 20%, 30%, 40% {
    transform: rotate(-5deg);
  }
`;

const spinnerFace = keyframes`
  0% {
    transform: translate(-50%, 20%);
  } 30% {
    transform: translate(0%, 30%);
  } 60% {
    transform: translate(50%, 20%);
  } 80% {
    transform: translate(0%, 30%);
  }
`;
const spinnerEye = keyframes`
  0% {
    transform: scaleY(1)
  } 80% {
    transform: scaleY(1)
  } 85% {
    transform: scaleY(0)
  } 90% {
    transform: scaleY(1)
  } 95% {
    transform: scaleY(0)
  }
`;

const moveUp = keyframes`
  0% {
    opacity: 0;
    transform: translateY(50px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
`;

const customMoveUp = (y: string) => keyframes`
  0% {
    opacity: 0;
    transform: translateY(${y});
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
`;

const moveDown = keyframes`
  0% {
    opacity: 0;
    transform: translateY(-50px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
`;

const moveLeft = keyframes`
  0% {
    opacity: 0;
    transform: translateX(50px);
  }
  100% {
    opacity: 1;
    transform: translateX(0);
  }
`;

const moveRight = keyframes`
  0% {
    opacity: 0;
    transform: translateX(-50px);
  }
  100% {
    opacity: 1;
    transform: translateX(0);
  }
`;

const wave = keyframes`
 from {
    transform: skew(0);
  }
  33% {
    transform: skew(2deg, 2deg);
  }
  66% {
    transform: skew(4deg, 4deg);
  }
  to {
    transform: skew(2deg, 2deg);
  }`;

const animation = {
  fadeIn,
  fadeOut,
  shake,
  littleShake,
  spinnerFace,
  spinnerEye,
  moveUp,
  customMoveUp,
  moveDown,
  moveRight,
  moveLeft,
  wave,
};

export default animation;
