import NotFound from '../NotFound';
import TokenExpired from '../TokenExpired';

// import { useParams } from 'react-router-dom';
import errorMessage from '@/constants/errorMessage';

interface ErrorPageProps {
  errorCode: keyof typeof errorMessage | undefined;
}

const ErrorPage: React.FC<ErrorPageProps> = ({ errorCode }) => {
  // const params = useParams();
  // console.log('params', params);

  if (!errorCode) return <></>;

  if (['A001', 'A002', 'A003'].includes(errorCode)) return <TokenExpired errorCode={errorCode} />;

  return <NotFound errorCode={errorCode} />;
};

export default ErrorPage;
