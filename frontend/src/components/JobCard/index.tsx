/**  @jsxImportSource @emotion/react */
import { useNavigate } from 'react-router-dom';

import CardTitle from '@/components/_common/CardTitle';

import apis from '@/apis';

import styles from './styles';

type JobCardProps = {
  jobName: string;
  id: number;
};

const JobCard = ({ jobName, id }: JobCardProps) => {
  const navigate = useNavigate();

  const handleClick = async () => {
    const {
      data: { active },
    } = await apis.getJobActive({ jobId: id });

    if (!active) {
      await apis.postNewTasks({ jobId: id });
    }

    navigate(id.toString());
  };

  return (
    <div css={styles.jobCard} onClick={handleClick}>
      <CardTitle>{jobName}</CardTitle>
    </div>
  );
};

export default JobCard;
