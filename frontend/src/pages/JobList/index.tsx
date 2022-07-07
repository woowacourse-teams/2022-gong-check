/**  @jsxImportSource @emotion/react */
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import PageTitle from '@/components/_common/PageTitle';

import JobCard from '@/components/JobCard';

import apis from '@/apis';

import styles from './styles';

type JobType = {
  id: number;
  name: string;
};

const JobList = () => {
  const [jobs, setJobs] = useState<Array<JobType>>([]);
  const { spaceId } = useParams();

  useEffect(() => {
    const getSpaces = async () => {
      const {
        data: { jobs },
      } = await apis.getJobs({ spaceId });

      setJobs(jobs);
    };

    getSpaces();
  }, []);

  return (
    <div css={styles.layout}>
      <PageTitle>
        체크리스트 목록(<span>{jobs.length}</span>)
      </PageTitle>
      <div css={styles.contents}>
        {jobs.map(job => (
          <JobCard jobName={job.name} key={job.id} id={job.id} />
        ))}
      </div>
    </div>
  );
};

export default JobList;
