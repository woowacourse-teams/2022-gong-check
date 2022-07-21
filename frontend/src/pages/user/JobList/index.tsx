import { IoIosArrowBack } from 'react-icons/io';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import JobCard from '@/components/user/JobCard';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';

import apiJobs from '@/apis/job';
import apiSpace from '@/apis/space';

import styles from './styles';

const JobList: React.FC = () => {
  const { spaceId } = useParams();

  const { data: jobsData } = useQuery(['jobs', spaceId], () => apiJobs.getJobs(spaceId), { suspense: true });
  const { data: spaceData } = useQuery(['spaces', spaceId], () => apiSpace.getSpace(spaceId), { suspense: true });

  const { goPreviousPage } = useGoPreviousPage();

  return (
    <div css={styles.layout}>
      <div css={styles.cover(spaceData?.imageUrl)}>
        <IoIosArrowBack css={styles.arrow} size={40} onClick={goPreviousPage} />
        <span css={styles.coverText}>{spaceData?.name}</span>
      </div>
      <span css={styles.text}>체크하실 업무를 선택해주세요.</span>
      {jobsData?.jobs.length === 0 ? (
        <div>관리자가 생성한 업무가 없어요</div>
      ) : (
        jobsData?.jobs.map(job => <JobCard jobName={job.name} key={job.id} id={job.id} />)
      )}
    </div>
  );
};

export default JobList;
