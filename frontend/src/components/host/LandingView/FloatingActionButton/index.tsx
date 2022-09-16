import useFloatingActionButton from './useFloatingActionButton';
import { RefObject } from 'react';

import styles from './styles';

const FloatingActionButton: React.FC<{ mainRef: RefObject<HTMLElement> }> = ({ mainRef }) => {
  const { eventNumber, onClick } = useFloatingActionButton(mainRef);

  return (
    <div>
      <div css={styles.floatingActionButton(eventNumber)} onClick={onClick}>
        <span>시작하기</span>
      </div>
    </div>
  );
};

export default FloatingActionButton;
