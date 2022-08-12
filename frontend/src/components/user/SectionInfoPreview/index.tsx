import { useEffect, useRef, useState } from 'react';
import { FaMapMarkedAlt } from 'react-icons/fa';

import styles from './styles';

interface SectionInfoPreviewProps {
  imageUrl: string;
  onClick: () => void;
}

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

const SectionInfoPreview: React.FC<SectionInfoPreviewProps> = ({ imageUrl, onClick }) => {
  const { isLoaded, lazyImageRef } = useLazyImage();

  return (
    <div css={styles.wrapper} onClick={onClick}>
      <div css={styles.imageWrapper}>
        <img css={styles.image} src={isLoaded ? imageUrl : ''} ref={lazyImageRef} />
      </div>
      <FaMapMarkedAlt css={styles.icon} size={24} />
    </div>
  );
};

export default SectionInfoPreview;
