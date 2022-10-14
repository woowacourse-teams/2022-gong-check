import ErrorHost from '@/ErrorBoundary/ErrorHost';
import { Suspense, useEffect, useMemo } from 'react';
import { Outlet } from 'react-router-dom';

import Loading from '@/components/common/Loading';

import styles from './styles';

const MANAGE_PATH = '/host/manage';

const HostLayout: React.FC = () => {
  const isManagePath = useMemo(() => location.pathname.includes(MANAGE_PATH), []);

  useEffect(() => {
    sessionStorage.setItem('tokenKey', 'host');
  }, []);

  return (
    <ErrorHost>
      <Suspense fallback={<Loading />}>
        <div css={styles.layout(isManagePath)}>
          <Outlet />
        </div>
      </Suspense>
    </ErrorHost>
  );
};

export default HostLayout;
