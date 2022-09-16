import { useRef, useState } from 'react';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

import { ScreenModeType } from '@/types';

import styles from './styles';

const HostViewSection3 = ({ screenMode }: { screenMode: ScreenModeType }) => {
  const [eventNumber, setEventNumber] = useState(0);
  const sectionRef = useRef<HTMLDivElement>(null);

  const { dimension, scrollInfo } = useOnContainerScroll(sectionRef, () => {
    const progress = Math.min(Math.max(0, scrollInfo.scrollY / dimension.windowHeight), 1) * 100;
    if (progress === 0) return;

    if (progress >= 20) {
      setEventNumber(1);
      return;
    }

    setEventNumber(0);
  });

  return (
    <section css={styles.layout} ref={sectionRef}>
      <div css={styles.content}>{eventNumber === 1 && <h1 css={styles.title(screenMode)}>지금 바로</h1>}</div>
    </section>
  );
};

export default HostViewSection3;
