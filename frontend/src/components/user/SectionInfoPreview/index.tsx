import { FaMapMarkedAlt } from 'react-icons/fa';

import useLazyImage from '@/hooks/useLazyLoading';

import styles from './styles';

interface SectionInfoPreviewProps {
  imageUrl: string;
  onClick: () => void;
}

const SectionInfoPreview: React.FC<SectionInfoPreviewProps> = ({ imageUrl, onClick }) => {
  const { isLoaded, targetRef: imageRef } = useLazyImage<HTMLImageElement>();

  return (
    <div css={styles.wrapper} onClick={onClick}>
      <div css={styles.imageWrapper}>
        <img css={styles.image} src={isLoaded ? imageUrl : ''} ref={imageRef} />
      </div>
      <FaMapMarkedAlt css={styles.icon} size={24} />
    </div>
  );
};

export default SectionInfoPreview;
