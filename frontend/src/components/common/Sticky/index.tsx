import styles from './style';
import { css } from '@emotion/react';
import { useEffect, useRef, useState } from 'react';

import useScroll from '@/hooks/useScroll';

import theme from '@/styles/theme';

interface StickyProps {
  children: React.ReactNode;
  defaultPosition: number;
}

const Sticky: React.FC<StickyProps> = ({ children, defaultPosition }) => {
  const [isActiveSticky, setIsActiveSticky] = useState(false);

  const progressBarRef = useRef<HTMLDivElement>(null);
  const { scrollPosition } = useScroll();

  useEffect(() => {
    const isActive = progressBarRef.current?.offsetTop! > defaultPosition;

    setIsActiveSticky(isActive);
  }, [scrollPosition]);

  return (
    <div css={styles.sticky(isActiveSticky)} ref={progressBarRef}>
      {children}
    </div>
  );
};

export default Sticky;
