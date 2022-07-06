/**  @jsxImportSource @emotion/react */
import PageTitle from '../../components/_common/PageTitle';
import styles from './styles';
import JobCard from '../../components/JobCard';

const jobs = [
  {
    id: 1,
    name: '청소',
  },
  {
    id: 2,
    name: '마감',
  },
];

const JobList = () => {
  return (
    <div css={styles.layout}>
      <PageTitle>
        체크리스트 목록(<span>2</span>)
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
