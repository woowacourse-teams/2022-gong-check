import ErrorUserTask from '@/ErrorBoundary/ErrorUserTask';
import ErrorUserToken from '@/ErrorBoundary/ErrorUserToken';
import { Global } from '@emotion/react';
import { Suspense, useEffect } from 'react';
import { Outlet, useNavigate, useParams } from 'react-router-dom';

import { ID } from '@/types';

import transitions from '@/styles/transitions';

import styles from './styles';

const UserLayout: React.FC = () => {
  const navigate = useNavigate();

  const { hostId } = useParams() as { hostId: ID };

  useEffect(() => {
    const tokenKey = sessionStorage.getItem('tokenKey');
    const token = localStorage.getItem(`${hostId}`);

    if (!tokenKey || tokenKey !== hostId || !token) {
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
