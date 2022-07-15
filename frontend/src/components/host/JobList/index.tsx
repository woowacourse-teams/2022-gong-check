import Button from '@/components/_common/Button';

import slackIcon from '@/assets/slackIcon.svg';

import styles from './styles';

const JOB_LIST = [
  { id: 1, name: '청소' },
  { id: 2, name: '마감' },
];

const JobList: React.FC = () => {
  const handleClickSlackButton = () => {
    alert('슬랙 버튼 클릭');
  };

  const handleClickNewJobButton = () => {
    alert('새 공간 생성 버튼 클릭');
  };

  const handleClickUpdateJobButton = () => {
    alert('작업 수정 버튼 클릭');
  };

  const handleClickDeleteJobButton = () => {
    alert('작업 삭제 버튼 클릭');
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
              <Button css={styles.deleteButton} onClick={handleClickDeleteJobButton}>
                삭제
              </Button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default JobList;
