import useJobList from './useJobList';
import { IoIosArrowBack } from 'react-icons/io';

import JobCard from '@/components/user/JobCard';

import styles from './styles';

const JobList: React.FC = () => {
  const { jobsData, spaceData, goPreviousPage } = useJobList();

  if (!jobsData || !spaceData) return <></>;

  return (
    <div css={styles.layout}>
      <div css={styles.cover(spaceData.imageUrl)}>
        <IoIosArrowBack css={styles.arrow} size={40} onClick={goPreviousPage} />
        <span css={styles.coverText}>{spaceData.name}</span>
      </div>
      <span css={styles.text}>체크하실 업무를 선택해주세요.</span>
      {jobsData.jobs.length === 0 ? (
        <div css={styles.empty}>관리자가 생성한 업무가 없어요</div>
      ) : (
        jobsData?.jobs.map(job => <JobCard jobName={job.name} key={job.id} jobId={job.id} />)
      )}
    </div>
  );
};

export default JobList;
