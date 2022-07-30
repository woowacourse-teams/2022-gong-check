import { FaMapMarkedAlt } from 'react-icons/fa';

import styles from './styles';

interface SectionInfoPreviewBoxProps {
  imageUrl: string;
  onClick: () => void;
}

const SectionInfoPreviewBox: React.FC<SectionInfoPreviewBoxProps> = ({ imageUrl, onClick }) => {
  return (
    <div css={styles.wrapper} onClick={onClick}>
      <div css={styles.imageWrapper}>
        <img css={styles.image} src={imageUrl} />
      </div>
      <FaMapMarkedAlt css={styles.icon} size={24} />
    </div>
  );
};

export default SectionInfoPreviewBox;
