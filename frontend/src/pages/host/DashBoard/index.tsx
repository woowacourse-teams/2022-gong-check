import submissions from '@/mock/submissions';
import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';

import JobListCard from '@/components/host/JobListCard';
import Submissions from '@/components/host/Submissions';

const DashBoard: React.FC = () => {
  const navigate = useNavigate();

  const handleClickSubmissionsDetail = () => {
    navigate('spaceRecord');
  };

  return (
    <div
      css={css`
        padding: 16px 48px;

        /* 임시 css 설정 - start*/
        display: flex;
        flex-direction: column;
        gap: 20px;
        /* 임시 css 설정 - end*/
      `}
    >
      <h1>공간 관리</h1>
      <JobListCard />
      <Submissions submissions={submissions} onClick={handleClickSubmissionsDetail} />
    </div>
  );
};

export default DashBoard;
