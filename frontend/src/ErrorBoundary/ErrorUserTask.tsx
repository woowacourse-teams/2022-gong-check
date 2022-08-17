import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { QueryErrorResetBoundary } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import { ID } from '@/types';

import errorMessage from '@/constants/errorMessage';

interface ErrorUserTaskProps {
  children: React.ReactNode;
}

const ErrorUserTask: React.FC<ErrorUserTaskProps> = ({ children }) => {
  const navigate = useNavigate();
  const { hostId } = useParams() as { hostId: ID };
  const [errorCode, setErrorCode] = useState<keyof typeof errorMessage>();

  const { openToast } = useToast();

  useEffect(() => {
    if (errorCode === 'R001' || errorCode === 'R002') {
      openToast('ERROR', errorMessage[`${errorCode}`]);
      navigate(`/enter/${hostId}/spaces`);
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

export default ErrorUserTask;
