import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';
import SlackUrlModal from '@/components/host/SlackUrlModal';

import useModal from '@/hooks/useModal';

import slackIcon from '@/assets/slackIcon.svg';

import styles from './styles';

const JOB_LIST = [
  { id: 1, name: '청소' },
  { id: 2, name: '마감' },
];

const JobListCard: React.FC = () => {
  const navigate = useNavigate();

  const { openModal } = useModal();

  const handleClickSlackButton = () => {
    openModal(<SlackUrlModal />);
  };

  const handleClickNewJobButton = () => {
    navigate('spaceCreate');
  };

  const handleClickUpdateJobButton = () => {
    navigate('jobCreate');
  };

  const handleClickDeleteJobButton = (jobId: number) => {
    alert(`작업 삭제 버튼 클릭! jobId:${jobId}`);
  };

  return (
    <div css={styles.layout}>
      <div css={styles.title}>
        <span>공간 업무 목록</span>
        <div>
          <Button css={styles.slackButton} onClick={handleClickSlackButton}>
            <img src={slackIcon} alt="슬랙" />
            <span>URL 편집</span>
          </Button>
          <Button css={styles.newJobButton} onClick={handleClickNewJobButton}>
            새 업무 생성
          </Button>
        </div>
      </div>
      <div css={styles.jobListWrapper}>
        {JOB_LIST.map(job => (
          <div css={styles.jobList} key={job.id}>
            <span>{job.name}</span>
            <div>
              <Button css={styles.updateButton} onClick={handleClickUpdateJobButton}>
                수정
              </Button>
              <Button
                css={styles.deleteButton}
                onClick={() => {
                  handleClickDeleteJobButton(job.id);
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
