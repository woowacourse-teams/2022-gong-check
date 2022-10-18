import useIntersectionObserver from './useIntersectionObserver';
import { RefObject, useEffect } from 'react';

interface ScrollInfo {
  scrollY: number;
  progress: number;
}

const useOnContainerScroll = (container: RefObject<HTMLElement>, onScroll: () => void) => {
  const { isLoaded } = useIntersectionObserver<HTMLElement>(container);

  const dimension = {
    top: 0,
    width: 0,
    height: 0,
    scrollHeight: 0,
    windowHeight: 0,
  };
  const scrollInfo: ScrollInfo = { scrollY: 0, progress: 0 };
  const buffer = 1000;

  let isRunning = false;
  let latestRequestedTime = 0;

  const onRunScroll = () => {
    latestRequestedTime = Date.now();

    if (isRunning) {
      return;
    }

    isRunning = true;

    const play = () => {
      requestAnimationFrame(() => {
        const now = Date.now() - buffer;
        const scrolledY = window.pageYOffset + window.innerHeight;
        scrollInfo.scrollY = Math.max(scrolledY - dimension.top, 0);
        scrollInfo.progress = Math.min(scrollInfo.scrollY / dimension.scrollHeight, 1);

        onScroll();

        if (now < latestRequestedTime) {
          play();
        } else {
          isRunning = false;
        }
      });
    };

    play();
  };

  const setDimension = () => {
    if (!container.current) {
      return;
    }

    dimension.top = container.current.getBoundingClientRect().top - document.body.getBoundingClientRect().top;
    dimension.width = container.current.clientWidth;
    dimension.height = container.current.clientHeight;
    dimension.scrollHeight = container.current.clientHeight;
    dimension.windowHeight = window.innerHeight;
  };

  useEffect(() => {
    if (isLoaded) {
      window.addEventListener('scroll', onRunScroll);
      window.addEventListener('resize', setDimension);
      setDimension();
    }
    return () => {
      window.removeEventListener('scroll', onRunScroll);
      window.removeEventListener('resize', setDimension);
    };
  }, [isLoaded]);

  return { dimension, scrollInfo };
};

export default useOnContainerScroll;
