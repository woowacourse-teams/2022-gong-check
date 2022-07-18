import ErrorUserToken from '@/errorBoundary/ErrorUserToken';
import { css } from '@emotion/react';
import { Suspense, useEffect } from 'react';
import { Outlet, useNavigate, useParams } from 'react-router-dom';

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
      <div
        css={css`
          display: flex;
          flex-direction: column;
          width: 400px;
          min-height: 100vh;
          background-color: white;
          position: absolute;
        `}
      >
        <Suspense fallback={<div>로딩 스피너</div>}>
          <Outlet />
        </Suspense>
      </div>
    </ErrorUserToken>
  );
};

export default UserLayout;
