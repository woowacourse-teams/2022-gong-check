import useDashBoard from './useDashBoard';
import { GoLinkExternal } from 'react-icons/go';

import Button from '@/components/common/Button';
import JobListCard from '@/components/host/JobListCard';
import SpaceDeleteButton from '@/components/host/SpaceDeleteButton';
import SpaceInfo from '@/components/host/SpaceInfo';
import Submissions from '@/components/host/Submissions';

import slackIcon from '@/assets/slackIcon.svg';

import styles from './styles';

const DashBoard: React.FC = () => {
  const {
    spaceId,
    spaceData,
    jobsData,
    submissionData,
    onClickSubmissionsDetail,
    onClickSlackButton,
    onClickLinkButton,
  } = useDashBoard();

  return (
    <div css={styles.layout}>
      <div css={styles.contents}>
        <div css={styles.buttons}>
          <Button css={styles.linkButton} onClick={onClickLinkButton}>
            <GoLinkExternal />
            <span>입장 링크 복사</span>
          </Button>
          <Button css={styles.slackButton} onClick={onClickSlackButton}>
            <img src={slackIcon} alt="슬랙" />
            <span>URL 편집</span>
          </Button>
        </div>
        <div css={styles.cardWrapper}>
          <SpaceInfo isEditMode={false} data={spaceData} />
          <JobListCard jobs={jobsData?.jobs || []} />
        </div>
        <Submissions submissions={submissionData?.submissions || []} onClick={onClickSubmissionsDetail} />
      </div>
      <div css={styles.deleteButtonWrapper}>
        <SpaceDeleteButton spaceId={spaceId} />
      </div>
    </div>
  );
};

export default DashBoard;
