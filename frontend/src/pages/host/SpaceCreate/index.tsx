import SpaceInfoCreateBox from '@/components/host/SpaceInfo/SpaceInfoCreateBox';

import styles from './styles';

const SpaceCreate: React.FC = () => {
  return (
    <div css={styles.layout}>
      <SpaceInfoCreateBox />
    </div>
  );
};

export default SpaceCreate;
