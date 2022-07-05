/**  @jsxImportSource @emotion/react */
import CardTitle from '../../components/_common/CardTitle';
import styles from './styles';

type JobCardProps = {
  jobName: string;
};

const JobCard = ({ jobName }: JobCardProps) => {
  return (
    <div css={styles.jobCard}>
      <CardTitle>{jobName}</CardTitle>
    </div>
  );
};

export default JobCard;
