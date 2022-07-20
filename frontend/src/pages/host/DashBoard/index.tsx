import useDashBoard from './useDashBoard';

import JobListCard from '@/components/host/JobListCard';
import SpaceInfo from '@/components/host/SpaceInfo';
import Submissions from '@/components/host/Submissions';

import styles from './styles';

const DashBoard: React.FC = () => {
  const { spaceData, jobsData, submissionData, onClickSubmissionsDetail } = useDashBoard();

  return (
    <div css={styles.layout}>
      <div css={styles.contents}>
        <div css={styles.cardWrapper}>
          <SpaceInfo isEditMode={false} data={spaceData} />
          <JobListCard jobs={jobsData?.jobs || []} />
        </div>
        <Submissions submissions={submissionData?.submissions || []} onClick={onClickSubmissionsDetail} />
      </div>
    </div>
  );
};

export default DashBoard;
