import { css } from '@emotion/react';
import React from 'react';

import Button from '@/components/_common/Button';

import theme from '@/styles/theme';

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
  onClick?: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

const Submissions: React.FC<SubmissionsProps> = ({ submissions, isFullSize = false, onClick }) => {
  return (
    <div css={styles.layout({ isFullSize })}>
      <div css={styles.header}>
        <p>
          공간 사용 내역(<span>{submissions.length}</span>)
        </p>

        {!isFullSize && (
          <Button css={styles.detailButton} type="button" onClick={onClick}>
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
                <td
                  css={css`
                    color: ${theme.colors.green};
                  `}
                >
                  {createdAt}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Submissions;
