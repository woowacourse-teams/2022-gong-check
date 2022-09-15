import { RefObject, useEffect, useRef, useState } from 'react';

interface ScrollInfo {
  scrollY: number;
  progress: number;
}

const useIntersectionObserver = <T extends Element>(targetRef: RefObject<T>, threshold: number = 0) => {
  const observerRef = useRef<IntersectionObserver>();
  const [isLoaded, setIsLoaded] = useState(false);

  const intersectionCallBack = (entries: IntersectionObserverEntry[], io: IntersectionObserver) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        setIsLoaded(true);
        return;
      }
      if (!entry.isIntersecting) {
        setIsLoaded(false);
        return;
      }
    });
  };

  useEffect(() => {
    if (!observerRef.current) {
      observerRef.current = new IntersectionObserver(intersectionCallBack, {
        threshold,
      });
    }

    targetRef.current && observerRef.current.observe(targetRef.current);

    return () => {
      observerRef.current?.disconnect();
    };
  }, [targetRef.current]);

  return { isLoaded };
};

const useOnContainerScroll = (container: RefObject<HTMLElement>, onScroll: (info: ScrollInfo) => void) => {
  const { isLoaded } = useIntersectionObserver<HTMLElement>(container);

  const dimension = {
    top: 0,
    width: 0,
    height: 0,
    scrollHeight: 0,
    windowHeight: 0,
  };
  const scrollInfo: ScrollInfo = {
    scrollY: 0,
    progress: 0,
  };
  const buffer = 1000;
  let isRunning = false;
  let latestRequestedTime = 0;

  const onFireScroll = () => {
    latestRequestedTime = Date.now();

    if (isRunning) {
      return;
    }

    isRunning = true;

    const play = () => {
      requestAnimationFrame(() => {
        const now = Date.now() - buffer;
        const scrolled = window.pageYOffset + window.innerHeight;
        scrollInfo.scrollY = Math.max(scrolled - dimension.top, 0);
        scrollInfo.progress = Math.min(scrollInfo.scrollY / dimension.scrollHeight, 1);
        onScroll(scrollInfo);
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
    if (container.current) {
      dimension.width = container.current.clientWidth;
      dimension.height = container.current.clientHeight;
      dimension.scrollHeight = container.current.clientHeight;
      dimension.top = container.current.getBoundingClientRect().top - document.body.getBoundingClientRect().top;
      dimension.windowHeight = window.innerHeight;
    }
    onFireScroll();
  };

  useEffect(() => {
    if (isLoaded) {
      window.addEventListener('scroll', onFireScroll);
      window.addEventListener('resize', setDimension);
      setDimension();
    }
    return () => {
      window.removeEventListener('scroll', onFireScroll);
      window.removeEventListener('resize', setDimension);
    };
  }, [isLoaded]);

  return {
    dimension,
    scrollInfo,
  };
};

export default useOnContainerScroll;
