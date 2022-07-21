import { useEffect, useRef, useState } from 'react';

import Button from '@/components/common/Button';

import styles from './styles';

interface JobControlProps {
  mode: 'create' | 'update';
  jobName: string;
  onChangeJobName: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onClickControlJob: () => void;
}

const JobControl: React.FC<JobControlProps> = ({ mode, jobName, onChangeJobName, onClickControlJob }) => {
  const inputRef = useRef<HTMLInputElement>(null);

  const [isEditing, setIsEditing] = useState(false);

  const onConfirmInput = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsEditing(false);
  };

  const onClickEdit = () => {
    setIsEditing(true);
  };

  useEffect(() => {
    inputRef.current?.focus();
  }, [isEditing]);

  return (
    <form css={styles.header} onSubmit={onConfirmInput}>
      {isEditing ? (
        <>
          <input css={styles.jobNameInput} ref={inputRef} defaultValue={jobName} onChange={onChangeJobName} />
          <Button type="submit">수정 확인</Button>
        </>
      ) : (
        <>
          <h1>{jobName}</h1>
          <div>
            <Button onClick={onClickEdit}>이름 수정</Button>
            <Button css={styles.createButton} onClick={onClickControlJob}>
              {mode === 'create' ? '업무 생성 완료' : '업무 수정 완료'}
            </Button>
          </div>
        </>
      )}
    </form>
  );
};

export default JobControl;
