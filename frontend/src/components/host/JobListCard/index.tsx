import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';
import SlackUrlModal from '@/components/host/SlackUrlModal';

import useModal from '@/hooks/useModal';

import { JobType } from '@/types';

import slackIcon from '@/assets/slackIcon.svg';

import styles from './styles';

interface JobListCardProps {
  jobs: JobType[] | [];
}

const JobListCard: React.FC<JobListCardProps> = ({ jobs }) => {
  const navigate = useNavigate();

  const { openModal } = useModal();

  const onClickSlackButton = () => {
    openModal(<SlackUrlModal jobs={jobs} />);
  };

  const onClickNewJobButton = () => {
    navigate('jobCreate');
  };

  const onClickUpdateJobButton = () => {
    navigate('jobCreate');
  };

  const onClickDeleteJobButton = (jobId: number | string) => {
    alert(`작업 삭제 버튼 클릭! jobId:${jobId}`);
  };

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
        {jobs.map(job => (
          <div css={styles.jobList} key={job.id}>
            <span>{job.name}</span>
            <div>
              <Button css={styles.updateButton} onClick={onClickUpdateJobButton}>
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
        ))}
      </div>
    </div>
  );
};

export default JobListCard;
