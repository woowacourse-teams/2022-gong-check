import useJobCard from './useJobCard';

import { ID } from '@/types';

import checklistImage from '@/assets/checklistImage.svg';

import styles from './styles';

interface JobCardProps {
  jobName: string;
  jobId: ID;
}

const JobCard: React.FC<JobCardProps> = ({ jobName, jobId }) => {
  const { onClickJobCard } = useJobCard(jobName, jobId);

  return (
    <div css={styles.jobCard} onClick={onClickJobCard}>
      <div css={styles.textWrapper}>
        <span>{jobName}</span>
        <span>체크리스트</span>
      </div>
      <img src={checklistImage} alt="체크리스트 아이콘" />
    </div>
  );
};

export default JobCard;
