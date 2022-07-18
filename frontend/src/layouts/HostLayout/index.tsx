import { Suspense } from 'react';
import { Outlet } from 'react-router-dom';

import styles from './styles';

const HostLayout: React.FC = () => {
  const isManagePath = location.pathname === '/host/manage';

  return (
    <div css={styles.layout(isManagePath)}>
      <Suspense fallback={<div>관리자 로딩 스피너</div>}>
        <Outlet />
      </Suspense>
    </div>
  );
};

export default HostLayout;
