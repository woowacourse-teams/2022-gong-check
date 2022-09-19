import useDashBoard from './useDashBoard';
import { BiTrash } from '@react-icons/all-files/bi/BiTrash';
import { GoLinkExternal } from '@react-icons/all-files/go/GoLinkExternal';

import Button from '@/components/common/Button';
import JobListCard from '@/components/host/JobListCard';
import SpaceInfoDisplayBox from '@/components/host/SpaceInfo/SpaceInfoDisplayBox';
import Submissions from '@/components/host/Submissions';

import slackIcon from '@/assets/slackIcon.svg';

import styles from './styles';

const DashBoard: React.FC = () => {
  const { spaceData, jobsData, submissionData, onClickSlackButton, onClickLinkButton, onClickDeleteSpace } =
    useDashBoard();

  return (
    <div css={styles.layout}>
      <div css={styles.contents}>
        <div css={styles.buttons}>
          <Button css={styles.linkButton} onClick={onClickLinkButton}>
            <GoLinkExternal />
            <span>입장 링크 복사</span>
          </Button>
          <Button css={styles.slackButton} onClick={onClickSlackButton}>
            <img src={slackIcon} alt="슬랙 URL 편집" />
            <span>URL 편집</span>
          </Button>
          <Button css={styles.spaceDeleteButton} onClick={onClickDeleteSpace}>
            <BiTrash />
            <span>공간 삭제</span>
          </Button>
        </div>
        <div css={styles.cardWrapper}>
          <SpaceInfoDisplayBox spaceData={spaceData!} />
          <JobListCard jobs={jobsData?.jobs || []} />
        </div>
        <Submissions submissions={submissionData?.submissions || []} />
      </div>
    </div>
  );
};

export default DashBoard;
