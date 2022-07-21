import useJobBox from './useJobBox';
import { useState } from 'react';

import Button from '@/components/common/Button';

import { JobType } from '@/types';

import styles from './styles';

interface JobBoxProps {
  job: JobType;
}

const JobBox: React.FC<JobBoxProps> = ({ job }) => {
  const { onClickUpdateJobButton, onClickDeleteJobButton } = useJobBox();
  const [isClicked, setIsClicked] = useState(false);

  return (
    <div
      css={styles.jobBox}
      onClick={() => {
        setIsClicked(prev => !prev);
      }}
    >
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
