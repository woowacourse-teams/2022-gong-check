import { useEffect, useRef, useState } from 'react';

interface LazyImageProps extends React.ImgHTMLAttributes<HTMLImageElement> {
  imageUrl: string | undefined;
}

const LazyImage: React.FC<LazyImageProps> = ({ imageUrl, ...props }) => {
  const lazyImageRef = useRef<HTMLImageElement>(null);
  const observerRef = useRef<IntersectionObserver>();
  const timeId = useRef<ReturnType<typeof setTimeout>>();

  const intersectionCallBack = (entries: IntersectionObserverEntry[], io: IntersectionObserver) => {
    entries.forEach(entry => {
      if (!entry.isIntersecting) return;

      entry.target.setAttribute('src', imageUrl || '');

      io.unobserve(entry.target);
    });
  };

  const onLoad = () => {
    if (timeId.current) {
      clearTimeout(timeId.current);
    }
  };

  const onError = () => {
    if (timeId.current) {
      clearTimeout(timeId.current);
    }

    if (!imageUrl) return;

    const newTimeId = setTimeout(() => {
      if (lazyImageRef.current) {
        lazyImageRef.current.src = imageUrl;
        clearTimeout(timeId.current);
      }
    }, 3000);

    timeId.current = newTimeId;
  };

  useEffect(() => {
    if (!observerRef.current) {
      observerRef.current = new IntersectionObserver(intersectionCallBack);
    }

    lazyImageRef.current && observerRef.current.observe(lazyImageRef.current);

    return () => {
      observerRef.current && observerRef.current.disconnect();
      clearTimeout(timeId.current);
    };
  }, []);

  return <img ref={lazyImageRef} onLoad={onLoad} onError={onError} {...props} />;
};

export default LazyImage;
