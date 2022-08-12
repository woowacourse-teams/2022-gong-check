import ErrorUserTask from '@/ErrorBoundary/ErrorUserTask';
import ErrorUserToken from '@/ErrorBoundary/ErrorUserToken';
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
    if (!localStorage.getItem('token') || entranceCodeData?.entranceCode !== hostId) {
      navigate(`/enter/${hostId}/pwd`);
    }
  }, []);

  return (
    <ErrorUserToken>
      <ErrorUserTask>
        <Suspense fallback={<></>}>
          <div css={styles.layout}>
            <Global styles={transitions} />
            <Outlet />
          </div>
        </Suspense>
      </ErrorUserTask>
    </ErrorUserToken>
  );
};

export default UserLayout;
