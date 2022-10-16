import NotFound from '../NotFound';
import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import { ID } from '@/types';

import errorMessage from '@/constants/errorMessage';

interface ErrorUserProps {
  errorCode: keyof typeof errorMessage | undefined;
}

const ErrorUser: React.FC<ErrorUserProps> = ({ errorCode }) => {
  const navigate = useNavigate();
  const { hostId } = useParams() as { hostId: ID };
  const { openToast } = useToast();

  useEffect(() => {
    if (errorCode === 'R001' || errorCode === 'R002' || errorCode === 'T003') {
      openToast('ERROR', errorMessage[`${errorCode}`]);
      navigate(`/enter/${hostId}/spaces`);
    }

    if (errorCode === 'A001' || errorCode === 'A002' || errorCode === 'A003') {
      navigate(`/enter/${hostId}/pwd`);
      openToast('ERROR', '회원 인증 시간이 만료 되었습니다.');
    }
  }, []);

  if (!errorCode) return <NotFound errorCode={errorCode} />;

  if (['A001', 'A002', 'A003'].includes(errorCode)) return <></>;

  return <NotFound errorCode={errorCode} />;
};

export default ErrorUser;
