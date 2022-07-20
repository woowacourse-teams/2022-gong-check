import useJobCard from './useJobCard';

import checklistImage from '@/assets/checklistImage.svg';

import styles from './styles';

interface JobCardProps {
  jobName: string;
  id: number | string;
}

const JobCard: React.FC<JobCardProps> = ({ jobName, id }) => {
  const { onClickJobCard } = useJobCard(id);

  return (
    <div css={styles.jobCard} onClick={onClickJobCard}>
      <div css={styles.textWrapper}>
        <span>{jobName}</span>
        <span>체크리스트</span>
      </div>
      <img src={checklistImage} alt="" />
    </div>
  );
};

export default JobCard;
