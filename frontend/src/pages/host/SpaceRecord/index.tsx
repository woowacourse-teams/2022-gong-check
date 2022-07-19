import submissions from '@/mock/submissions';
import { useParams } from 'react-router-dom';

import Submissions from '@/components/host/Submissions';

import styles from './styles';

const SpaceRecord: React.FC = () => {
  const { spaceId } = useParams();
  // TODO: API 연동

  return (
    <div css={styles.layout}>
      <Submissions submissions={submissions} isFullSize={true} />
    </div>
  );
};

export default SpaceRecord;
