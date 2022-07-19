import submissions from '@/mock/submissions';
import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import JobListCard from '@/components/host/JobListCard';
import SpaceInfo from '@/components/host/SpaceInfo';
import Submissions from '@/components/host/Submissions';

import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';

import styles from './styles';

const SPACE_INFO_DATA = {
  id: 1,
  name: '잠실 캠퍼스',
  imageUrl: 'https://velog.velcdn.com/images/cks3066/post/258f92c1-32be-4acb-be30-1eb64635c013/image.jpg',
};

const JOBS_DATA = [
  {
    id: 1,
    name: '청소',
  },
  {
    id: 2,
    name: '마감',
  },
];

const DashBoard: React.FC = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams();

  // const { data } = useQuery(['space', spaceId], () => apiSpace.getSpace({ spaceId }), { suspense: true });
  // const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId), { suspense: true });

  // SpaceInfo 공간정보 수정하기 구현 해야 합니다.
  const onClickSubmissionsDetail = () => {
    navigate('spaceRecord');
  };

  // if (!jobsData) return <div />;
  // <JobListCard jobs={jobsData.jobs} />

  return (
    <div css={styles.layout}>
      <h1>공간 관리</h1>
      <div css={styles.contents}>
        <div css={styles.cardWrapper}>
          <SpaceInfo isEditMode={false} data={SPACE_INFO_DATA} />
          <JobListCard jobs={JOBS_DATA} />
        </div>
        <Submissions submissions={submissions} onClick={onClickSubmissionsDetail} />
      </div>
    </div>
  );
};

export default DashBoard;
