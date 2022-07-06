/**  @jsxImportSource @emotion/react */
import { useNavigate } from 'react-router-dom';

import CardTitle from '@/components/_common/CardTitle';

import styles from './styles';

type JobCardProps = {
  jobName: string;
  id: number;
};

const JobCard = ({ jobName, id }: JobCardProps) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(id.toString());
  };

  return (
    <div css={styles.jobCard} onClick={handleClick}>
      <CardTitle>{jobName}</CardTitle>
    </div>
  );
};

export default JobCard;
