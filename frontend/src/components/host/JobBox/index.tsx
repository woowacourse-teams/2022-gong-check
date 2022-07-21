import useJobBox from './useJobBox';

import Button from '@/components/common/Button';

import { JobType } from '@/types';

import styles from './styles';

interface JobBoxProps {
  job: JobType;
}

const JobBox: React.FC<JobBoxProps> = ({ job }) => {
  const { isClicked, onClickUpdateJobButton, onClickDeleteJobButton, onClickJobBox } = useJobBox();

  return (
    <div css={styles.jobBox} onClick={onClickJobBox}>
      <span>{job.name}</span>
      {isClicked && (
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
      )}
    </div>
  );
};

export default JobBox;
