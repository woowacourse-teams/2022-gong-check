import { Suspense, useMemo } from 'react';
import { Outlet } from 'react-router-dom';

import styles from './styles';

const MANAGE_PATH = '/host/manage';

const HostLayout: React.FC = () => {
  const isManagePath = useMemo(() => location.pathname.includes(MANAGE_PATH), []);

  return (
    <div css={styles.layout(isManagePath)}>
      <Suspense fallback={<div>관리자 로딩 스피너</div>}>
        <Outlet />
      </Suspense>
    </div>
  );
};

export default HostLayout;
