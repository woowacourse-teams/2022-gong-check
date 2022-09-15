import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';

import emptyFolder from '@/assets/emptyFolder.png';

import styles from './styles';

const columns = ['제출자', '업무', '제출일자'];

interface Submission {
  submissionId: number;
  jobId: number;
  jobName: string;
  author: string;
  createdAt: string;
}

interface SubmissionsProps {
  submissions: Submission[];
  isFullSize?: boolean;
}

function formatDate(targetDate: string) {
  const date = new Date(targetDate);

  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hours = date.getHours();
  const minutes = date.getMinutes();

  return `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;
}

const Submissions: React.FC<SubmissionsProps> = ({ submissions, isFullSize = false }) => {
  const navigate = useNavigate();

  const onClickSubmissionsDetail = () => {
    navigate('spaceRecord');
  };

  return (
    <div css={styles.layout({ isFullSize })}>
      <div css={styles.header}>
        <p>사용 내역</p>

        {!isFullSize && (
          <Button css={styles.detailButton} type="button" onClick={onClickSubmissionsDetail}>
            더보기
          </Button>
        )}
      </div>
      <div>
        <table css={styles.table({ isFullSize })}>
          <thead>
            <tr>
              {columns.map(column => (
                <th key={column}>{column}</th>
              ))}
            </tr>
          </thead>

          <tbody>
            {submissions.map(({ submissionId, author, jobName, createdAt }) => (
              <tr key={submissionId}>
                <td>{author}</td>
                <td>{jobName}</td>
                <td css={styles.greenText}>{formatDate(createdAt)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Submissions;
