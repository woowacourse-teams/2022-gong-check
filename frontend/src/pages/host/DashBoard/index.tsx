import { css } from '@emotion/react';

import JobList from '@/components/host/JobList';

const DashBoard: React.FC = () => {
  return (
    <div
      css={css`
        padding: 16px 48px;
      `}
    >
      <h1>공간 관리</h1>
      <JobList />
    </div>
  );
};

export default DashBoard;
