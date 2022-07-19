import { IoIosArrowBack } from 'react-icons/io';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import JobCard from '@/components/user/JobCard';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';

import apis from '@/apis';

import styles from './styles';

// spaceId로 공간에 대한 정보 조회
const SPACE_DATA = {
  name: '잠실 캠퍼스',
  imageUrl: 'https://velog.velcdn.com/images/cks3066/post/258f92c1-32be-4acb-be30-1eb64635c013/image.jpg ',
};

const JobList: React.FC = () => {
  const { spaceId } = useParams();

  const { data } = useQuery(['jobs', spaceId], () => apis.getJobs({ spaceId }), { suspense: true });

  const { goPreviousPage } = useGoPreviousPage();

  return (
    <div css={styles.layout}>
      <div css={styles.cover(SPACE_DATA.imageUrl)}>
        <IoIosArrowBack css={styles.arrow} size={40} onClick={goPreviousPage} />
        <span css={styles.coverText}>{SPACE_DATA.name}</span>
      </div>
      <span css={styles.text}>체크하실 업무를 선택해주세요.</span>
      {data?.jobs.map(job => (
        <JobCard jobName={job.name} key={job.id} id={job.id} />
      ))}
    </div>
  );
};

export default JobList;
