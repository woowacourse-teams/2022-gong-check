import useJobCard from './useJobCard';

import CardTitle from '@/components/_common/CardTitle';

import styles from './styles';

interface JobCardProps {
  jobName: string;
  id: number;
}

const JobCard: React.FC<JobCardProps> = ({ jobName, id }) => {
  const { onClickJobCard } = useJobCard(id);

  return (
    <div css={styles.jobCard} onClick={onClickJobCard}>
      <CardTitle>{jobName}</CardTitle>
    </div>
  );
};

export default JobCard;
