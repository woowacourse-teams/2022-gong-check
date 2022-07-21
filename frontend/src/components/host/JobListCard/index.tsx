import useJobListCard from './useJobListCard';

import Button from '@/components/common/Button';

import { JobType } from '@/types';

import emptyFolder from '@/assets/emptyFolder.png';
import slackIcon from '@/assets/slackIcon.svg';

import styles from './styles';

interface JobListCardProps {
  jobs: JobType[] | [];
}

const JobListCard: React.FC<JobListCardProps> = ({ jobs }) => {
  const { onClickSlackButton, onClickNewJobButton, onClickUpdateJobButton, onClickDeleteJobButton } =
    useJobListCard(jobs);

  return (
    <div css={styles.layout}>
      <div css={styles.title}>
        <span>공간 업무 목록</span>
        <div>
          <Button css={styles.slackButton} onClick={onClickSlackButton}>
            <img src={slackIcon} alt="슬랙" />
            <span>URL 편집</span>
          </Button>
          <Button css={styles.newJobButton} onClick={onClickNewJobButton}>
            새 업무 생성
          </Button>
        </div>
      </div>
      <div css={styles.jobListWrapper}>
        {jobs.length === 0 ? (
          <div css={styles.empty}>
            <img src={emptyFolder} alt="" />
            <div>생성된 업무가 없어요.</div>
          </div>
        ) : (
          jobs.map(job => (
            <div css={styles.jobList} key={job.id}>
              <span>{job.name}</span>
              <div>
                <Button
                  css={styles.updateButton}
                  onClick={() => {
                    onClickUpdateJobButton(job.id);
                  }}
                >
                  수정
                </Button>
                <Button
                  css={styles.deleteButton}
                  onClick={() => {
                    onClickDeleteJobButton(job.id);
                  }}
                >
                  삭제
                </Button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default JobListCard;
