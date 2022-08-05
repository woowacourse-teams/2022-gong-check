import Button from '@/components/common/Button';

import useEditInput from '@/hooks/useEditInput';

import styles from './styles';

interface JobControlProps {
  mode: 'create' | 'update';
  jobName: string;
  onChangeJobName: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const JobControl: React.FC<JobControlProps> = ({ mode, jobName, onChangeJobName }) => {
  return (
    <div css={styles.header}>
      <input
        css={styles.jobNameInput}
        value={jobName}
        placeholder="새 업무"
        maxLength={10}
        onChange={onChangeJobName}
        required
      />
      <Button css={styles.createButton} type="submit">
        {mode === 'create' ? '생성 완료' : '수정 완료'}
      </Button>
    </div>
  );
};

export default JobControl;
