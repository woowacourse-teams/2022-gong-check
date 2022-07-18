import { css } from '@emotion/react';
import { Suspense, useMemo } from 'react';
import { Outlet } from 'react-router-dom';

const MANAGE_PATH = '/host/manage';

const HostLayout: React.FC = () => {
  const isManagePath = useMemo(() => location.pathname.includes(MANAGE_PATH), []);

  return (
    <Suspense fallback={<div>관리자 로딩 스피너</div>}>
      <div
        css={css`
          display: flex;
          flex-direction: column;
          width: 100vw;
          height: 100vh;
          background-color: #f9faff;
          padding-left: ${isManagePath ? '224px' : '0'};
        `}
      >
        <Outlet />
      </div>
    </Suspense>
  );
};

export default HostLayout;
