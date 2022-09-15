import { FaMapMarkedAlt } from 'react-icons/fa';

import LazyImage from '@/components/common/LazyImage';

import styles from './styles';

interface SectionInfoPreviewProps {
  imageUrl: string;
  onClick: () => void;
}

const SectionInfoPreview: React.FC<SectionInfoPreviewProps> = ({ imageUrl, onClick }) => {
  return (
    <div css={styles.wrapper} onClick={onClick}>
      <div css={styles.imageWrapper}>
        <LazyImage css={styles.image} imageUrl={imageUrl} />
      </div>
      <FaMapMarkedAlt css={styles.icon} size={24} />
    </div>
  );
};

export default SectionInfoPreview;
