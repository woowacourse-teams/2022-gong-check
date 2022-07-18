import submissions from '@/mock/submissions';

import Submissions from '@/components/host/Submissions';

import styles from './styles';

const SpaceRecord: React.FC = () => {
  return (
    <div css={styles.layout}>
      <Submissions submissions={submissions} isFullSize={true} />
    </div>
  );
};

export default SpaceRecord;
