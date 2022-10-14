import { BsFillExclamationTriangleFill } from '@react-icons/all-files/bs/BsFillExclamationTriangleFill';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';

import useToast from '@/hooks/useToast';

import errorMessage from '@/constants/errorMessage';

import styles from './styles';

interface NotFoundProps {
  errorCode?: keyof typeof errorMessage | undefined;
}

const NotFound: React.FC<NotFoundProps> = ({ errorCode }) => {
  const navigate = useNavigate();
  const { openToast } = useToast();

  useEffect(() => {
    if (!errorCode) {
      openToast('ERROR', '존재하지 않는 페이지입니다.');
      return;
    }

    openToast('ERROR', errorMessage[`${errorCode}`]);
  }, []);

  return (
    <div css={styles.layout}>
      <div css={styles.content}>
        <BsFillExclamationTriangleFill css={styles.mark} size={80} />
      </div>
      <div css={styles.description}>존재하지 않는 페이지입니다!</div>
      <Button css={styles.button} onClick={() => navigate(-1)}>
        이전 페이지로 이동
      </Button>
    </div>
  );
};

export default NotFound;
