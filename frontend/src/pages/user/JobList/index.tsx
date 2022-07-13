import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import PageTitle from '@/components/_common/PageTitle';

import JobCard from '@/components/JobCard';

import apis from '@/apis';

import styles from './styles';

const JobList = () => {
  const { spaceId } = useParams();

  const { data } = useQuery(['jobs', spaceId], () => apis.getJobs({ spaceId }), { suspense: true });

  return (
    <div css={styles.layout}>
      <PageTitle>
        체크리스트 목록(<span>{data?.jobs.length}</span>)
      </PageTitle>
      <div css={styles.contents}>
        {data?.jobs.map(job => (
          <JobCard jobName={job.name} key={job.id} id={job.id} />
        ))}
      </div>
    </div>
  );
};

export default JobList;
