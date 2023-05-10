import { useEffect, useRef, useState } from 'react';

const useLazyLoading = <T extends Element>(threshold: number = 0) => {
  const targetRef = useRef<T>(null);
  const observerRef = useRef<IntersectionObserver>();
  const [isLoaded, setIsLoaded] = useState(false);

  const intersectionCallBack = (entries: IntersectionObserverEntry[], io: IntersectionObserver) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        io.unobserve(entry.target);
        setIsLoaded(true);
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
  }, []);

  return { isLoaded, targetRef };
};

export default useLazyLoading;
