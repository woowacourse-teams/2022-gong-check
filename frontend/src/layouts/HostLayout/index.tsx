import { css } from '@emotion/react';
import { Suspense } from 'react';
import { Outlet } from 'react-router-dom';

const HostLayout: React.FC = () => {
  return (
    <Suspense fallback={<div>로딩 스피너</div>}>
      <div
        css={css`
          display: flex;
          flex-direction: column;
          width: 100vw;
          height: 100vh;
        `}
      >
        <Outlet />
      </div>
    </Suspense>
  );
};

export default HostLayout;
