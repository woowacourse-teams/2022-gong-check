import { AxiosError } from 'axios';
import { ErrorBoundary } from 'react-error-boundary';
import { QueryErrorResetBoundary } from 'react-query';
import { useNavigate } from 'react-router-dom';

import errorMessage from '@/constants/errorMessage';

interface ErrorHostTokenProps {
  children: React.ReactNode;
}

const ErrorHostToken: React.FC<ErrorHostTokenProps> = ({ children }) => {
  const navigate = useNavigate();

  return (
    <QueryErrorResetBoundary>
      <ErrorBoundary
        fallbackRender={({ error }) => {
          const err = error as AxiosError<{ errorCode: keyof typeof errorMessage }>;
          const errorCode = err.response?.data.errorCode;

          if (errorCode === 'A002' || errorCode === 'A003') {
            navigate(`/host`);
          }

          return <></>;
        }}
      >
        {children}
      </ErrorBoundary>
    </QueryErrorResetBoundary>
  );
};

export default ErrorHostToken;
