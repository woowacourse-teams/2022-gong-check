import NotFound from '../NotFound';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import errorMessage from '@/constants/errorMessage';

interface ErrorHostProps {
  errorCode: keyof typeof errorMessage | undefined;
}

const ErrorHost: React.FC<ErrorHostProps> = ({ errorCode }) => {
  const navigate = useNavigate();
  const { openToast } = useToast();

  useEffect(() => {
    if (errorCode === 'A001' || errorCode === 'A002' || errorCode === 'A003') {
      navigate('/host');
      openToast('ERROR', '로그아웃 되었습니다.');
    }
  }, []);

  if (!errorCode) return <NotFound />;

  if (['A001', 'A002', 'A003'].includes(errorCode)) return <></>;

  return <NotFound errorCode={errorCode} />;
};

export default ErrorHost;
