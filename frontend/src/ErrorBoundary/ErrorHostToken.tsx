import { AxiosError } from 'axios';
import { ErrorBoundary } from 'react-error-boundary';
import { useQueryErrorResetBoundary } from 'react-query';

import ErrorPage from '@/pages/errorPages/ErrorPage';

import errorMessage from '@/constants/errorMessage';

interface ErrorHostTokenProps {
  children: React.ReactNode;
}

const ErrorHostToken: React.FC<ErrorHostTokenProps> = ({ children }) => {
  const { reset } = useQueryErrorResetBoundary();

  return (
    <ErrorBoundary
      onReset={reset}
      fallbackRender={({ error }) => {
        const err = error as AxiosError<{ errorCode: keyof typeof errorMessage }>;
        const errorCode = err.response?.data.errorCode;

        return <ErrorPage errorCode={errorCode} />;
      }}
    >
      {children}
    </ErrorBoundary>
  );
};

export default ErrorHostToken;
