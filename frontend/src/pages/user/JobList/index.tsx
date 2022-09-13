import useJobList from './useJobList';
import { IoIosArrowBack } from 'react-icons/io';

import JobCard from '@/components/user/JobCard';

import DEFAULT_IMAGE from '@/assets/defaultSpaceImage.webp';

import styles from './styles';

const JobList: React.FC = () => {
  const { jobsData, spaceData, goPreviousPage } = useJobList();

  return (
    <div css={styles.layout}>
      <div css={styles.cover(spaceData?.imageUrl || DEFAULT_IMAGE)}>
        <IoIosArrowBack css={styles.arrow} size={30} onClick={goPreviousPage} />
        <span css={styles.coverText}>{spaceData?.name}</span>
      </div>
      {jobsData?.jobs.length === 0 ? (
        <div css={styles.empty}>관리자가 생성한 업무가 없어요</div>
      ) : (
        jobsData?.jobs.map(job => <JobCard jobName={job.name} key={job.id} jobId={job.id} />)
      )}
    </div>
  );
};

export default JobList;
