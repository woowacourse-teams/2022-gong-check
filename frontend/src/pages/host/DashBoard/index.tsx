import { css } from '@emotion/react';
import { useNavigate } from 'react-router-dom';

import JobList from '@/components/host/JobList';
import Submissions from '@/components/host/Submissions';

const submissions = [
  {
    submissionId: 1,
    jobId: 1,
    jobName: '청소',
    author: '어썸오',
    createdAt: 'YYYY-MM-DDTHH:MM:SS',
  },
  {
    submissionId: 2,
    jobId: 2,
    jobName: '마감',
    author: '어썸오',
    createdAt: 'YYYY-MM-DDTHH:MM:SS',
  },
  {
    submissionId: 3,
    jobId: 2,
    jobName: '마감',
    author: '어썸오',
    createdAt: 'YYYY-MM-DDTHH:MM:SS',
  },
  {
    submissionId: 4,
    jobId: 2,
    jobName: '마감',
    author: '어썸오',
    createdAt: 'YYYY-MM-DDTHH:MM:SS',
  },
  {
    submissionId: 5,
    jobId: 2,
    jobName: '마감',
    author: '어썸오',
    createdAt: 'YYYY-MM-DDTHH:MM:SS',
  },
  {
    submissionId: 6,
    jobId: 2,
    jobName: '마감',
    author: '어썸오',
    createdAt: 'YYYY-MM-DDTHH:MM:SS',
  },
  {
    submissionId: 7,
    jobId: 2,
    jobName: '마감',
    author: '어썸오',
    createdAt: 'YYYY-MM-DDTHH:MM:SS',
  },
  {
    submissionId: 8,
    jobId: 2,
    jobName: '마감',
    author: '어썸오',
    createdAt: 'YYYY-MM-DDTHH:MM:SS',
  },
];

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
      <JobList />
      <Submissions submissions={submissions} onClick={handleClickSubmissionsDetail} />
    </div>
  );
};

export default DashBoard;
