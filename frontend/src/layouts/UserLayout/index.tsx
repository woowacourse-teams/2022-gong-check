import ErrorUserBoundary from '@/ErrorBoundary/ErrorUserBoundary';
import { Global } from '@emotion/react';
import { Suspense, useEffect } from 'react';
import { useQuery } from 'react-query';
import { Outlet, useNavigate, useParams } from 'react-router-dom';

import apiHost from '@/apis/host';

import { ID } from '@/types';

import transitions from '@/styles/transitions';

import styles from './styles';

const UserLayout: React.FC = () => {
  const navigate = useNavigate();

  const { hostId } = useParams() as { hostId: ID };

  const { data: entranceCodeData } = useQuery(['hostId', hostId], apiHost.getEntranceCode);

  useEffect(() => {
    sessionStorage.setItem('tokenKey', `${hostId}`);
    const token = localStorage.getItem(`${hostId}`);



    console.log('123123');
    if (!token) {
      navigate(`/enter/${hostId}/pwd`);
    }
  }, []);

  return (
    <ErrorUserBoundary>
      <div css={styles.layout}>
        <Global styles={transitions} />
        <Suspense fallback={<div css={styles.fallback} />}>
          <Outlet />
        </Suspense>
      </div>
    </ErrorUserBoundary>
  );
};

export default UserLayout;
