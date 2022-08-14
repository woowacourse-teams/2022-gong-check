import { FaMapMarkedAlt } from 'react-icons/fa';

import useLazyImage from '@/hooks/useLazyImage';

import styles from './styles';

interface SectionInfoPreviewProps {
  imageUrl: string;
  onClick: () => void;
}

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
