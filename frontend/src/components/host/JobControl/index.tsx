import { useEffect, useRef, useState } from 'react';

import Button from '@/components/common/Button';

import styles from './styles';

interface JobControlProps {
  jobName: string;
  onChangeJobName: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onClickCreateNewJob: () => void;
}

const JobControl: React.FC<JobControlProps> = ({ jobName, onChangeJobName, onClickCreateNewJob }) => {
  const inputRef = useRef<HTMLInputElement>(null);

  const [isEditing, setIsEditing] = useState(false);

  const onClickConfirm = () => {
    setIsEditing(false);
  };

  const onClickEdit = () => {
    setIsEditing(true);
  };

  useEffect(() => {
    inputRef.current?.focus();
  }, [isEditing]);

  return (
    <div css={styles.header}>
      {isEditing ? (
        <>
          <input css={styles.jobNameInput} ref={inputRef} defaultValue={jobName} onChange={onChangeJobName} />
          <Button onClick={onClickConfirm}>수정 확인</Button>
        </>
      ) : (
        <>
          <h1>{jobName}</h1>
          <div>
            <Button onClick={onClickEdit}>이름 수정</Button>
            <Button css={styles.createButton} onClick={onClickCreateNewJob}>
              업무 생성 완료
            </Button>
          </div>
        </>
      )}
    </div>
  );
};

export default JobControl;