import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import Submissions from '@/components/host/Submissions';

import apiSubmission from '@/apis/submission';

import styles from './styles';

const SpaceRecord: React.FC = () => {
  const { spaceId } = useParams();
  const { data: submissionData } = useQuery(['submissions', spaceId], () => apiSubmission.getSubmission({ spaceId }), {
    suspense: true,
  });

  return (
    <div css={styles.layout}>
      <Submissions submissions={submissionData?.submissions || []} isFullSize={true} />
    </div>
  );
};

export default SpaceRecord;
