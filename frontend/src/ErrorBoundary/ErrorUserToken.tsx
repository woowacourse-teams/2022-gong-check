import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { QueryErrorResetBoundary } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import { ID } from '@/types';

import errorMessage from '@/constants/errorMessage';

interface ErrorUserTokenProps {
  children: React.ReactNode;
}

const ErrorUserToken: React.FC<ErrorUserTokenProps> = ({ children }) => {
  const navigate = useNavigate();
  const { hostId } = useParams() as { hostId: ID };
  const [errorCode, setErrorCode] = useState<keyof typeof errorMessage>();

  useEffect(() => {
    if (errorCode === 'A002' || errorCode === 'A003') {
      navigate(`/enter/${hostId}/pwd`);
    }
  }, [errorCode]);

  return (
    <QueryErrorResetBoundary>
      <ErrorBoundary
        fallbackRender={({ error }) => {
          const err = error as AxiosError<{ errorCode: keyof typeof errorMessage }>;
          const newErrorCode = err.response?.data.errorCode;

          setErrorCode(newErrorCode);

          return <></>;
        }}
      >
        {children}
      </ErrorBoundary>
    </QueryErrorResetBoundary>
  );
};

export default ErrorUserToken;
