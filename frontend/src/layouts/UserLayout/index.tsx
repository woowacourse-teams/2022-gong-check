import ErrorUserToken from '@/ErrorBoundary/ErrorUserToken';
import { Global } from '@emotion/react';
import { Suspense, useEffect } from 'react';
import { Outlet, useNavigate, useParams } from 'react-router-dom';

import transitions from '@/styles/transitions';

import styles from './styles';

const UserLayout: React.FC = () => {
  const navigate = useNavigate();
  const { hostId } = useParams();

  useEffect(() => {
    if (!localStorage.getItem('token')) {
      navigate(`/enter/${hostId}/pwd`);
    }
  }, []);

  return (
    <Suspense fallback={<></>}>
      <ErrorUserToken>
        <div css={styles.layout}>
          <Global styles={transitions} />
          <Outlet />
        </div>
      </ErrorUserToken>
    </Suspense>
  );
};

export default UserLayout;
