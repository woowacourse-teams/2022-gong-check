import Submissions from '@/components/host/Submissions';

import styles from './styles';

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

const SpaceRecord: React.FC = () => {
  return (
    <div css={styles.layout}>
      <Submissions submissions={submissions} isFullSize={true} />
    </div>
  );
};

export default SpaceRecord;
