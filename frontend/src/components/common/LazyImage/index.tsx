import { useEffect, useRef, useState } from 'react';

interface LazyImageProps extends React.ImgHTMLAttributes<HTMLImageElement> {
  imageUrl: string | undefined;
}

const LazyImage: React.FC<LazyImageProps> = ({ imageUrl, ...props }) => {
  const lazyImageRef = useRef<HTMLImageElement>(null);
  const observerRef = useRef<IntersectionObserver>();

  const intersectionCallBack = (entries: IntersectionObserverEntry[], io: IntersectionObserver) => {
    entries.forEach(entry => {
      if (!entry.isIntersecting) return;

      entry.target.setAttribute('src', imageUrl || '');
      io.unobserve(entry.target);
    });
  };

  useEffect(() => {
    if (!observerRef.current) {
      observerRef.current = new IntersectionObserver(intersectionCallBack);
    }

    lazyImageRef.current && observerRef.current.observe(lazyImageRef.current);

    return () => observerRef.current && observerRef.current.disconnect();
  }, []);

  return <img ref={lazyImageRef} {...props} />;
};

export default LazyImage;
