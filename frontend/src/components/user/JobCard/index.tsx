import useJobCard from './useJobCard';

import { ID } from '@/types';

import checklistImage from '@/assets/checklistImage2.svg';

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
        <span>체크하러 가기</span>
      </div>
      <img css={styles.img} src={checklistImage} alt="" />
    </div>
  );
};

export default JobCard;
