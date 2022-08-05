import useJobBox from './useJobBox';

import Button from '@/components/common/Button';

import { JobType } from '@/types';

import styles from './styles';

interface JobBoxProps {
  job: JobType;
}

const JobBox: React.FC<JobBoxProps> = ({ job }) => {
  const { onClickUpdateJobButton, onClickDeleteJobButton } = useJobBox();

  return (
    <div css={styles.jobBox}>
      <span>{job.name}</span>
      <div>
        <Button
          css={styles.updateButton}
          onClick={() => {
            onClickUpdateJobButton(job.id, job.name);
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
  );
};

export default JobBox;
