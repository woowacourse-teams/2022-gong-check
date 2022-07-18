import { css } from '@emotion/react';
import { AxiosResponse, AxiosError } from 'axios';
import { Suspense, useEffect } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { QueryErrorResetBoundary } from 'react-query';
import { Outlet, useNavigate, useParams } from 'react-router-dom';

const EXPIRED_TOKEN_TEXT = '만료된 토큰입니다.';
const NOT_TOKEN_TEXT = '헤더에 토큰 값이 정상적으로 존재하지 않습니다.';

const UserLayout: React.FC = () => {
  const navigate = useNavigate();
  const { hostId } = useParams();

  useEffect(() => {
    if (!localStorage.getItem('user')) {
      navigate(`/enter/${hostId}/pwd`);
    }
  }, []);

  return (
    <QueryErrorResetBoundary>
      <ErrorBoundary
        fallbackRender={({ error }) => {
          const err = error as AxiosError;
          const res = err.response as AxiosResponse;
          const message = res.data.message;

          if (message === EXPIRED_TOKEN_TEXT) {
            localStorage.removeItem('user');
            navigate(`/enter/${hostId}/pwd`);
          }

          if (message === NOT_TOKEN_TEXT) {
            navigate(`/enter/${hostId}/pwd`);
          }

          return <></>;
        }}
      >
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
      </ErrorBoundary>
    </QueryErrorResetBoundary>
  );
};

export default UserLayout;
