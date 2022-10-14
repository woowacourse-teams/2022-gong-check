import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';

import useToast from '@/hooks/useToast';

import errorMessage from '@/constants/errorMessage';

import homeCover_360w from '@/assets/homeCover-360w.webp';
import homeCover_480w from '@/assets/homeCover-480w.webp';
import homeCover_fallback from '@/assets/homeCover-fallback.png';

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
        <div css={styles.homeCoverWrapper}>
          <picture css={styles.homeCover}>
            <source media={`(max-width: 360px)`} type="image/webp" srcSet={homeCover_360w} />
            <source type="image/webp" srcSet={homeCover_480w} />
            <img src={homeCover_fallback} alt="" />
          </picture>
        </div>
      </div>
      <div css={styles.description}>존재하지 않는 페이지입니다!</div>
      <Button onClick={() => navigate(-1)}>이전 페이지로 이동</Button>
      <Button onClick={() => navigate('/host')}>Home</Button>
    </div>
  );
};

export default NotFound;
