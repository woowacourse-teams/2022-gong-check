/**  @jsxImportSource @emotion/react */
import PageTitle from '../../components/_common/PageTitle';
import styles from './styles';
import JobCard from '../../components/JobCard';

const jobs = ['청소', '마감'];

const JobList = () => {
  return (
    <div css={styles.layout}>
      <PageTitle>
        체크리스트 목록(<span>2</span>)
      </PageTitle>
      <div css={styles.contents}>
        {jobs.map((job, index) => (
          <JobCard jobName={job} key={index} />
        ))}
      </div>
    </div>
  );
};

export default JobList;
