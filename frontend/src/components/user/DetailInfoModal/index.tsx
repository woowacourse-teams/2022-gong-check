import Dimmer from '@/components/common/Dimmer';

import homeCover_fallback from '@/assets/homeCover-fallback.png';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

export interface DetailInfoModalProps {
  name: string;
  imageUrl: string;
  description: string;
}

const getImageUrl = (url: string): string => {
  return url === '' ? homeCover_fallback : url;
};

const DetailInfoModal: React.FC<DetailInfoModalProps> = ({ name, imageUrl, description }) => {
  return (
    <ModalPortal>
      <Dimmer>
        <div css={styles.container}>
          <h1 css={styles.title}>{name}</h1>
          <div css={styles.imageWrapper}>
            <img css={styles.image} src={getImageUrl(imageUrl)} />
          </div>
          <span css={styles.description}>{description}</span>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default DetailInfoModal;
