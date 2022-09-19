import { css } from '@emotion/react';
import { IoIosArrowDown } from '@react-icons/all-files/io/IoIosArrowDown';
import React, { useEffect, useRef, useState } from 'react';

import useScroll from '@/hooks/useScroll';

import homeCover from '@/assets/homeCover.png';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const CIRCLE_SIZE = 80;

const MainSection: React.FC = () => {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  const positionRef = useRef({ x: window.innerWidth / 2, y: window.innerHeight / 2 });

  const { scrollPosition } = useScroll();
  const scrollPositionInt = scrollPosition + 3;

  const [isFull, setIsFull] = useState(false);

  const onMouseMove = (e: React.MouseEvent<HTMLElement, MouseEvent>, scrollPositionInt: number) => {
    const canvas = canvasRef.current;
    const ctx = canvas?.getContext('2d');
    if (!canvas || !ctx) return;

    const target = e.target as HTMLInputElement;
    const localName = target.localName;

    if (localName !== 'canvas') return;

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

    const radius = CIRCLE_SIZE * scrollPositionInt - scrollPositionInt * 2;

    ctx.beginPath();
    let circlePath = new Path2D();
    circlePath.arc(x, y, radius, 0, Math.PI * 2);
    circlePath.rect(0, 0, window.innerWidth, window.innerHeight);
    ctx.clip(circlePath, 'evenodd');
    ctx.fillStyle = '#000000';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.closePath();

    if (scrollPositionInt >= 12) {
      setIsFull(true);
    } else {
      setIsFull(false);
    }
  }, [scrollPositionInt]);

  // useEffect(() => {
  //   const canvas = canvasRef.current;
  //   const ctx = canvas?.getContext('2d');
  //   if (!canvas || !ctx) return;

  //   const scale = window.devicePixelRatio;

  //   canvas.width = Math.floor(window.innerWidth * scale);
  //   canvas.height = Math.floor(window.innerHeight * scale);
  //   ctx.scale(scale, scale);

  //   const x = window.innerWidth / 2;
  //   const y = window.innerHeight / 2;

  //   const radius = CIRCLE_SIZE * scrollPositionInt;

  //   ctx.beginPath();
  //   let circlePath = new Path2D();
  //   circlePath.arc(x, y, radius, 0, Math.PI * 2);
  //   circlePath.rect(0, 0, window.innerWidth, window.innerHeight);
  //   ctx.clip(circlePath, 'evenodd');
  //   ctx.fillStyle = '#000000';
  //   ctx.fillRect(0, 0, canvas.width, canvas.height);
  //   ctx.closePath();
  // }, []);

  return (
    <>
      <section
        css={css`
          width: 100vw;
          height: 100vh;
          overflow: hidden;
          ${isFull
            ? `
            transform: translateY(68px);
          `
            : `
          position: fixed;
          top: 0;
          z-index: 100;
          `}
        `}
        onMouseMove={e => onMouseMove(e, scrollPositionInt)}
      >
        <div
          css={css`
            height: 100vh;
            position: relative;
            top: 0;
            width: 100%;
            z-index: 1;
            background-color: ${theme.colors.background};
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
              opacity: 0.85;
            `}
            ref={canvasRef}
          ></canvas>
        </div>
      </section>
      <div
        css={css`
          margin-bottom: ${isFull ? '20vh' : '100vh'};
          background-color: ${theme.colors.background};
        `}
      />
    </>
  );
};

export default MainSection;
