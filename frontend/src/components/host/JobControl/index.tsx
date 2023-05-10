import Button from '@/components/common/Button';

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
        placeholder="업무 이름을 입력해주세요. (10자 이내)"
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
