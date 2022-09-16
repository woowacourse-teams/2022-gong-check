import React, { useEffect, useRef, useState } from 'react';
import { IoIosArrowDown } from 'react-icons/io';

import useScroll from '@/hooks/useScroll';

import homeCover from '@/assets/homeCover.png';

import styles from './styles';

const CIRCLE_SIZE = 80;

const HeroSection: React.FC = () => {
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

  return (
    <>
      <section css={styles.layout(isFull)} onMouseMove={e => onMouseMove(e, scrollPositionInt)}>
        <div css={styles.content}>
          <div css={styles.homeCoverWrapper}>
            <div id="커버" css={styles.homeCover}>
              <img src={homeCover} alt="" />
            </div>
          </div>
          <div css={styles.arrowDownWrapper}>
            <span>아래로 내려주세요.</span>
            <IoIosArrowDown />
          </div>
          <canvas css={styles.canvas} ref={canvasRef}></canvas>
        </div>
      </section>
      <div css={styles.bottomWrapper(isFull)} />
    </>
  );
};

export default HeroSection;
