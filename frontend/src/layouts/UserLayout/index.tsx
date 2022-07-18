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
    <Suspense fallback={<div>유저 레이아웃 로딩 스피너</div>}>
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
