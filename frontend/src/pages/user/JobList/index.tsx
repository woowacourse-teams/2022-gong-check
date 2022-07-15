import { css } from '@emotion/react';
import { IoIosArrowBack } from 'react-icons/io';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import JobCard from '@/components/JobCard';

import apis from '@/apis';

import theme from '@/styles/theme';

import styles from './styles';

const SPACE_DATA = {
  name: '잠실 캠퍼스',
  imageUrl: 'https://velog.velcdn.com/images/cks3066/post/258f92c1-32be-4acb-be30-1eb64635c013/image.jpg ',
};

const JobList: React.FC = () => {
  const { spaceId } = useParams();

  // spaceId로 공간에 대한 정보 조회
  const { data } = useQuery(['jobs', spaceId], () => apis.getJobs({ spaceId }), { suspense: true });

  return (
    <div css={styles.layout}>
      <div
        css={css`
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          height: 200px;
          background-image: linear-gradient(${theme.colors.shadow40}, ${theme.colors.shadow40}),
            url(${SPACE_DATA.imageUrl});
          padding: 16px;
          background-size: cover;
        `}
      >
        <IoIosArrowBack
          css={css`
            color: white;
          `}
          size={30}
        />
        <span
          css={css`
            color: ${theme.colors.white};
            font-size: 32px;
            text-shadow: 0 0 4px black;
            background-image: linear-gradient(transparent 90%, ${theme.colors.primary} 10%);
            width: fit-content;
          `}
        >
          {SPACE_DATA.name}
        </span>
      </div>
      <div css={styles.contents}>
        {data?.jobs.map(job => (
          <JobCard jobName={job.name} key={job.id} id={job.id} />
        ))}
      </div>
    </div>
  );
};

export default JobList;
