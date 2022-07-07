/**  @jsxImportSource @emotion/react */
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import CardTitle from '@/components/_common/CardTitle';

import apis from '@/apis';

import styles from './styles';

type JobCardProps = {
  jobName: string;
  id: number;
};

const JobCard = ({ jobName, id }: JobCardProps) => {
  const [active, setActive] = useState<boolean>(false);

  useEffect(() => {
    const getJobActive = async () => {
      const {
        data: { active },
      } = await apis.getJobActive({ jobId: id });

      setActive(active);
    };

    getJobActive();
  }, []);

  const navigate = useNavigate();

  const handleClick = () => {
    navigate(id.toString(), { state: { active } });
  };

  return (
    <div css={styles.jobCard} onClick={handleClick}>
      <CardTitle>{jobName}</CardTitle>
    </div>
  );
};

export default JobCard;
