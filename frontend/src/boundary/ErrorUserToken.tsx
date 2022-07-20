import { AxiosError } from 'axios';
import { ErrorBoundary } from 'react-error-boundary';
import { QueryErrorResetBoundary } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

const EXPIRED_TOKEN_TEXT = '만료된 토큰입니다.';
const NOT_TOKEN_TEXT = '헤더에 토큰 값이 정상적으로 존재하지 않습니다.';

interface ErrorUserTokenProps {
  children: React.ReactNode;
}

const ErrorUserToken: React.FC<ErrorUserTokenProps> = ({ children }) => {
  const navigate = useNavigate();
  const { hostId } = useParams();

  return (
    <QueryErrorResetBoundary>
      <ErrorBoundary
        fallbackRender={({ error }) => {
          const err = error as AxiosError<{ message: string }>;
          const message = err.response?.data.message;

          if (message === EXPIRED_TOKEN_TEXT || message === NOT_TOKEN_TEXT) {
            localStorage.removeItem('token');
            navigate(`/enter/${hostId}/pwd`);
          }

          return <></>;
        }}
      >
        {children}
      </ErrorBoundary>
    </QueryErrorResetBoundary>
  );
};

export default ErrorUserToken;
