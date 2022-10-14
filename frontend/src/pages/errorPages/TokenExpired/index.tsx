import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import errorMessage from '@/constants/errorMessage';

interface TokenExpiredProps {
  errorCode: keyof typeof errorMessage | undefined;
}

const TokenExpired: React.FC<TokenExpiredProps> = ({ errorCode }) => {
  const navigate = useNavigate();
  const { openToast } = useToast();

  useEffect(() => {
    navigate('/host');
    openToast('ERROR', '회원 인증 시간이 만료 되었습니다.');
  }, []);
  return <></>;
};

export default TokenExpired;
