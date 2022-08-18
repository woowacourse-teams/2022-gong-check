import { css } from '@emotion/react';
import React, { useEffect, useRef } from 'react';
import { IoIosArrowDown } from 'react-icons/io';

import useScroll from '@/hooks/useScroll';

import homeCover from '@/assets/homeCover.png';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const CIRCLE_SIZE = 80;

const MainSection: React.FC = () => {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  const positionRef = useRef({ x: 0, y: 0 });

  const { scrollPosition } = useScroll();
  const scrollPositionInt = scrollPosition + 3;

  const onMouseMove = (e: React.MouseEvent<HTMLElement, MouseEvent>, scrollPositionInt: number) => {
    const canvas = canvasRef.current;
    const ctx = canvas?.getContext('2d');
    if (!canvas || !ctx) return;

    positionRef.current = {
      x: e.nativeEvent.offsetX,
      y: e.nativeEvent.offsetY,
    };

    const scale = window.devicePixelRatio;
    canvas.width = Math.floor(window.innerWidth * scale);
    canvas.height = Math.floor(window.innerHeight * scale);
    ctx.scale(scale, scale);

    const x = e.nativeEvent.offsetX;
    const y = e.nativeEvent.offsetY;

    const radius = CIRCLE_SIZE * scrollPositionInt;

    ctx.beginPath();
    let circlePath = new Path2D();
    circlePath.arc(x, y, radius, 0, Math.PI * 2);
    circlePath.rect(0, 0, window.innerWidth, window.innerHeight);
    ctx.clip(circlePath, 'evenodd');
    ctx.fillStyle = '#000000';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.closePath();
  };

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas?.getContext('2d');
    if (!canvas || !ctx) return;

    const scale = window.devicePixelRatio;

    canvas.width = Math.floor(window.innerWidth * scale);
    canvas.height = Math.floor(window.innerHeight * scale);
    ctx.scale(scale, scale);

    const x = positionRef.current.x;
    const y = positionRef.current.y;

    const radius = CIRCLE_SIZE * scrollPositionInt;

    ctx.beginPath();
    let circlePath = new Path2D();
    circlePath.arc(x, y, radius, 0, Math.PI * 2);
    circlePath.rect(0, 0, window.innerWidth, window.innerHeight);
    ctx.clip(circlePath, 'evenodd');
    ctx.fillStyle = '#000000';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.closePath();
  }, [scrollPositionInt]);

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas?.getContext('2d');
    if (!canvas || !ctx) return;

    const scale = window.devicePixelRatio;

    canvas.width = Math.floor(window.innerWidth * scale);
    canvas.height = Math.floor(window.innerHeight * scale);
    ctx.scale(scale, scale);

    const x = window.innerWidth / 2;
    const y = window.innerHeight / 2;

    const radius = CIRCLE_SIZE * scrollPositionInt;

    ctx.beginPath();
    let circlePath = new Path2D();
    circlePath.arc(x, y, radius, 0, Math.PI * 2);
    circlePath.rect(0, 0, window.innerWidth, window.innerHeight);
    ctx.clip(circlePath, 'evenodd');
    ctx.fillStyle = '#000000';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.closePath();
  }, []);

  return (
    <section
      css={css`
        width: 100vw;
        height: 100vh;
        overflow: hidden;
        position: relative;
      `}
      onMouseMove={e => onMouseMove(e, scrollPositionInt)}
    >
      <div
        css={css`
          height: 100vh;
          position: relative;
          top: -1px;
          width: 100%;
          z-index: 1;
        `}
      >
        <div
          css={css`
            position: absolute;
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
            height: 100%;
          `}
        >
          <div
            id="커버"
            css={css`
              margin: 1em 0;
              animation: ${animation.wave} 2s alternate linear infinite;
            `}
          >
            <img src={homeCover} alt="" />
          </div>
        </div>
        <div
          css={css`
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
          `}
        >
          <span>아래로 내려주세요.</span>
          <IoIosArrowDown />
        </div>

        <canvas
          css={css`
            height: 100%;
            left: 50%;
            position: relative;
            top: 50%;
            -webkit-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
            width: 100%;
            z-index: 2;
            opacity: 0.9;
          `}
          ref={canvasRef}
        ></canvas>
      </div>
    </section>
  );
};

export default MainSection;
