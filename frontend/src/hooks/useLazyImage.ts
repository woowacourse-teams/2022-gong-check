import { useEffect, useRef, useState } from 'react';

const useLazyImage = () => {
  const lazyImageRef = useRef<HTMLImageElement>(null);
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
      observerRef.current = new IntersectionObserver(intersectionCallBack);
    }

    lazyImageRef.current && observerRef.current.observe(lazyImageRef.current);
  }, []);

  return { isLoaded, lazyImageRef };
};

export default useLazyImage;
