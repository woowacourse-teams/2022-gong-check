import useDashBoard from './useDashBoard';
import { GoLinkExternal } from 'react-icons/go';

import Button from '@/components/common/Button';
import ImageBox from '@/components/host/ImageBox';
import JobListCard from '@/components/host/JobListCard';
import SpaceDeleteButton from '@/components/host/SpaceDeleteButton';
import SpaceInfo from '@/components/host/SpaceInfo';
import Submissions from '@/components/host/Submissions';

import slackIcon from '@/assets/slackIcon.svg';

import styles from './styles';

const DashBoard: React.FC = () => {
  const { spaceId, spaceData, jobsData, submissionData, onClickSlackButton, onClickLinkButton } = useDashBoard();

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
          <SpaceDeleteButton spaceId={spaceId} spaceName={spaceData?.name || ''} />
        </div>
        <div css={styles.cardWrapper}>
          <SpaceInfo type="read" space={spaceData!}>
            <ImageBox type="read" imageUrl={spaceData?.imageUrl || ''} />
          </SpaceInfo>
          <JobListCard jobs={jobsData?.jobs || []} />
        </div>
        <Submissions submissions={submissionData?.submissions || []} />
      </div>
    </div>
  );
};

export default DashBoard;
