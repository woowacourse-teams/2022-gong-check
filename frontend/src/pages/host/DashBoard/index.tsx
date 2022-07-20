import submissions from '@/mock/submissions';
import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import JobListCard from '@/components/host/JobListCard';
import SpaceInfo from '@/components/host/SpaceInfo';
import Submissions from '@/components/host/Submissions';

import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';

import styles from './styles';

const DashBoard: React.FC = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams();

  const { data: spaceData } = useQuery(['space', spaceId], () => apiSpace.getSpace({ spaceId }), {
    suspense: true,
  });
  const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId), { suspense: true });

  // SpaceInfo 공간정보 수정하기 구현 해야 합니다.
  const onClickSubmissionsDetail = () => {
    navigate('spaceRecord');
  };

  if (!jobsData || !spaceData) return <></>;

  return (
    <div css={styles.layout}>
      <div css={styles.contents}>
        <div css={styles.cardWrapper}>
          <SpaceInfo isEditMode={false} data={spaceData} />
          <JobListCard jobs={jobsData.jobs} />
        </div>
        <Submissions submissions={submissions} onClick={onClickSubmissionsDetail} />
      </div>
    </div>
  );
};

export default DashBoard;
