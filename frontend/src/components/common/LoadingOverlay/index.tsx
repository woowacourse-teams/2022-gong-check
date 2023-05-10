import Loading from '../Loading';

import styles from './styles';

const LoadingOverlay: React.FC = () => {
  return (
    <div css={styles.loadingOverlay}>
      <Loading />
    </div>
  );
};

export default LoadingOverlay;
