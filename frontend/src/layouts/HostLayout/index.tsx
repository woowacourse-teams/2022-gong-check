import { Suspense, useMemo } from 'react';
import { Outlet } from 'react-router-dom';

import Loading from '@/components/common/Loading';

import styles from './styles';

const MANAGE_PATH = '/host/manage';

const HostLayout: React.FC = () => {
  const isManagePath = useMemo(() => location.pathname.includes(MANAGE_PATH), []);

  return (
    <Suspense fallback={<Loading />}>
      <div css={styles.layout(isManagePath)}>
        <Outlet />
      </div>
    </Suspense>
  );
};

export default HostLayout;
