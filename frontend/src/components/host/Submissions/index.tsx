import { css } from '@emotion/react';
import React from 'react';
import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';

import emptyFolder from '@/assets/emptyFolder.png';

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
}

const Submissions: React.FC<SubmissionsProps> = ({ submissions, isFullSize = false }) => {
  const navigate = useNavigate();

  const onClickSubmissionsDetail = () => {
    navigate('spaceRecord');
  };

  return (
    <div css={styles.layout({ isFullSize })}>
      <div css={styles.header}>
        <p>공간 사용 내역</p>

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
            {submissions.length === 0 ? (
              <tr css={styles.empty}>
                <td>
                  <img src={emptyFolder} alt="" />
                  <div>제출된 내역이 없어요.</div>
                </td>
              </tr>
            ) : (
              <>
                {submissions.map(({ submissionId, author, jobName, createdAt }) => (
                  <tr key={submissionId}>
                    <td>{author}</td>
                    <td>{jobName}</td>
                    <td css={styles.greenText}>{createdAt}</td>
                  </tr>
                ))}
              </>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Submissions;
