import { RefObject, useEffect, useRef, useState } from 'react';

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

export default useIntersectionObserver;
