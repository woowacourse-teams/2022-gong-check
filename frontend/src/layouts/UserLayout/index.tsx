import ErrorUserToken from '@/errorBoundary/ErrorUserToken';
import { Global } from '@emotion/react';
import { Suspense, useEffect } from 'react';
import { Outlet, useNavigate, useParams } from 'react-router-dom';

import transitions from '@/styles/transitions';

import styles from './styles';

const UserLayout: React.FC = () => {
  const navigate = useNavigate();
  const { hostId } = useParams();

  useEffect(() => {
    if (!localStorage.getItem('user')) {
      navigate(`/enter/${hostId}/pwd`);
    }
  }, []);

  return (
    <ErrorUserToken>
      <Suspense fallback={<div>로딩 스피너</div>}>
        <Global styles={transitions} />
        <div css={styles.layout}>
          <Outlet />
        </div>
      </Suspense>
    </ErrorUserToken>
  );
};

export default UserLayout;
