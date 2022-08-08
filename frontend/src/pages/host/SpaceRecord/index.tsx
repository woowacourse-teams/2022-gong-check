import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import Submissions from '@/components/host/Submissions';

import apiSubmission from '@/apis/submission';

import { ID } from '@/types';

import styles from './styles';

const SpaceRecord: React.FC = () => {
  const { spaceId } = useParams() as { spaceId: ID };
  const { data: submissionData } = useQuery(['submissions', spaceId], () => apiSubmission.getSubmission(spaceId));

  return (
    <div css={styles.layout}>
      <Submissions submissions={submissionData?.submissions || []} isFullSize={true} />
    </div>
  );
};

export default SpaceRecord;
