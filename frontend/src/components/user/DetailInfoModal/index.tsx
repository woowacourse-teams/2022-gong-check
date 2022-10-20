import { useState } from 'react';

import Dimmer from '@/components/common/Dimmer';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

export interface DetailInfoModalProps {
  name: string;
  imageUrl: string;
  description: string;
}

const DetailInfoModal: React.FC<DetailInfoModalProps> = ({ name, imageUrl, description }) => {
  const [isLoadImg, setIsLoadImg] = useState<boolean>(true);

  const onLoad = () => {
    setIsLoadImg(false);
  };

  return (
    <ModalPortal>
      <Dimmer>
        <div css={styles.container}>
          <h1 css={styles.title}>{name}</h1>
          <div css={styles.imageWrapper}>
            {imageUrl !== '' && isLoadImg && <div css={styles.skeletonImage}></div>}
            {imageUrl !== '' && <img css={styles.image} onLoad={onLoad} src={imageUrl} />}
          </div>
          <span css={styles.description}>{description}</span>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default DetailInfoModal;
