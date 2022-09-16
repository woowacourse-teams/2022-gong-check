import { RefObject, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

import styles from './styles';

const FloatingActionButton: React.FC<{ mainRef: RefObject<HTMLElement> }> = ({ mainRef }) => {
  const navigate = useNavigate();
  const [eventNumber, setEventNumber] = useState(0);

  const { scrollInfo } = useOnContainerScroll(mainRef, () => {
    if (scrollInfo.progress === 1) {
      setEventNumber(1);
      return;
    }

    setEventNumber(0);
  });

  const onClick = () => {
    navigate('/host');
  };

  return (
    <div>
      <div css={styles.floatingActionButton(eventNumber)} onClick={onClick}>
        <span>시작하기</span>
      </div>
    </div>
  );
};

export default FloatingActionButton;
