import Button from '@/components/common/Button';

import useEditInput from '@/hooks/useEditInput';

import styles from './styles';

interface JobControlProps {
  mode: 'create' | 'update';
  jobName: string;
  onChangeJobName: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onClickControlJob: () => void;
}

const JobControl: React.FC<JobControlProps> = ({ mode, jobName, onChangeJobName, onClickControlJob }) => {
  const { isEditing, inputRef, confirmInput, editInput: onClickEdit } = useEditInput();

  const onConfirmInput = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    confirmInput();
  };

  return (
    <form css={styles.header} onSubmit={onConfirmInput}>
      {isEditing ? (
        <>
          <input css={styles.jobNameInput} ref={inputRef} defaultValue={jobName} onChange={onChangeJobName} />
          <Button type="submit">확인</Button>
        </>
      ) : (
        <>
          <h1 onClick={onClickEdit}>{jobName}</h1>
          <div>
            <Button onClick={onClickEdit}>이름 수정</Button>
            <Button css={styles.createButton} onClick={onClickControlJob}>
              {mode === 'create' ? '업무 생성' : '업무 수정'}
            </Button>
          </div>
        </>
      )}
    </form>
  );
};

export default JobControl;
