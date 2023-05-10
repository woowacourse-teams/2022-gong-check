import { useEffect, useRef, useState } from 'react';

import useScroll from '@/hooks/useScroll';

import screenSize from '@/constants/screenSize';

const CIRCLE_SIZE = window.innerWidth > screenSize.MOBILE ? 80 : 60;

const SCROLL_INTERVAL = 3;

const useHeroSection = () => {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  const positionRef = useRef({ x: window.innerWidth / 2, y: window.innerHeight / 2 });

  const { scrollPosition } = useScroll();

  const scrollPositionInt = scrollPosition + SCROLL_INTERVAL;

  const [isFull, setIsFull] = useState(false);

  const createCanvas = (
    canvas: HTMLCanvasElement,
    ctx: CanvasRenderingContext2D,
    x: number,
    y: number,
    radius: number
  ) => {
    const scale = window.devicePixelRatio;
    canvas.width = Math.floor(window.innerWidth * scale);
    canvas.height = Math.floor(window.innerHeight * scale);
    ctx.scale(scale, scale);

    ctx.beginPath();
    let circlePath = new Path2D();
    circlePath.arc(x, y, radius, 0, Math.PI * 2);
    circlePath.rect(0, 0, window.innerWidth, window.innerHeight);
    ctx.clip(circlePath, 'evenodd');
    ctx.fillStyle = '#000000';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.closePath();
  };

  const onMouseMove = (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
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

    const x = e.nativeEvent.offsetX;
    const y = e.nativeEvent.offsetY;
    const radius = CIRCLE_SIZE * scrollPositionInt;

    createCanvas(canvas, ctx, x, y, radius);
  };

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas?.getContext('2d');
    if (!canvas || !ctx) return;

    const x = positionRef.current.x;
    const y = positionRef.current.y;

    let radius = CIRCLE_SIZE * scrollPositionInt - scrollPositionInt * 2;

    if (radius < 0) radius = 0;

    createCanvas(canvas, ctx, x, y, radius);

    if (scrollPositionInt >= 12) {
      setIsFull(true);
    } else {
      setIsFull(false);
    }
  }, [scrollPositionInt]);

  return { isFull, onMouseMove, canvasRef };
};

export default useHeroSection;
