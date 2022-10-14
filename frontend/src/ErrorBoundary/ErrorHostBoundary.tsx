import { AxiosError } from 'axios';
import { ErrorBoundary } from 'react-error-boundary';
import { useQueryErrorResetBoundary } from 'react-query';

import ErrorHost from '@/pages/errorPages/ErrorHost';

import errorMessage from '@/constants/errorMessage';

interface ErrorHostBoundaryProps {
  children: React.ReactNode;
}

const ErrorHostBoundary: React.FC<ErrorHostBoundaryProps> = ({ children }) => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <ErrorBoundary
      onReset={reset}
      fallbackRender={({ error }) => {
        const err = error as AxiosError<{ errorCode: keyof typeof errorMessage }>;
        const errorCode = err.response?.data.errorCode;

        return <ErrorHost errorCode={errorCode} />;
      }}
    >
      {children}
    </ErrorBoundary>
  );
};

export default ErrorHostBoundary;
