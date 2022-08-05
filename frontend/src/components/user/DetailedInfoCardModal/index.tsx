import Dimmer from '@/components/common/Dimmer';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

interface DetailedInfoCardModalProps {
  name: string;
  imageUrl: string;
  description: string;
}

const DetailedInfoCardModal: React.FC<DetailedInfoCardModalProps> = ({ name, imageUrl, description }) => {
  return (
    <ModalPortal>
      <Dimmer mode="mobile">
        <div css={styles.container}>
          <h1 css={styles.title}>{name}</h1>
          <div css={styles.imageWrapper}>
            <img css={styles.image} src={imageUrl} />
          </div>
          <span css={styles.description}>{description}</span>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default DetailedInfoCardModal;
