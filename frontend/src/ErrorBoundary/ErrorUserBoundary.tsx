import { AxiosError } from 'axios';
import { ErrorBoundary } from 'react-error-boundary';
import { useQueryErrorResetBoundary } from 'react-query';

import ErrorUser from '@/pages/errorPages/ErrorUser';

import errorMessage from '@/constants/errorMessage';

interface ErrorUserBoundaryProps {
  children: React.ReactNode;
}

const ErrorUserBoundary: React.FC<ErrorUserBoundaryProps> = ({ children }) => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <ErrorBoundary
      onReset={reset}
      fallbackRender={({ error }) => {
        const err = error as AxiosError<{ errorCode: keyof typeof errorMessage }>;
        const errorCode = err.response?.data.errorCode;

        return <ErrorUser errorCode={errorCode} />;
      }}
    >
      {children}
    </ErrorBoundary>
  );
};

export default ErrorUserBoundary;
