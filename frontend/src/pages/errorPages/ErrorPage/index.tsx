import NotFound from '../NotFound';
import TokenExpired from '../TokenExpired';

import errorMessage from '@/constants/errorMessage';

interface ErrorPageProps {
  errorCode: keyof typeof errorMessage | undefined;
}

const ErrorPage: React.FC<ErrorPageProps> = ({ errorCode }) => {
  if (!errorCode) return <NotFound errorCode={errorCode} />;

  if (['A001', 'A002', 'A003'].includes(errorCode)) return <TokenExpired errorCode={errorCode} />;

  return <NotFound errorCode={errorCode} />;
};

export default ErrorPage;
