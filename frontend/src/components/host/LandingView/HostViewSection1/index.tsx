import { useRef, useState } from 'react';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

import { ScreenModeType } from '@/types';

import styles from './styles';

const HostViewSection1 = ({ screenMode }: { screenMode: ScreenModeType }) => {
  const [eventNumber, setEventNumber] = useState(0);
  const sectionRef = useRef<HTMLDivElement>(null);

  const { dimension, scrollInfo } = useOnContainerScroll(sectionRef, () => {
    const progress = Math.min(Math.max(0, scrollInfo.scrollY / dimension.windowHeight), 1) * 100;
    if (progress === 0) return;

    if (progress >= 80) {
      setEventNumber(1);
      return;
    }
    setEventNumber(0);
  });

  return (
    <section css={styles.layout(screenMode)} ref={sectionRef}>
      <div css={styles.content}>
        <h1 css={styles.title(screenMode)}>
          직접 내 공간을 <b>생성</b>하고 <b>관리</b>하고싶다면?
        </h1>
        {eventNumber === 1 && (
          <h1 css={styles.subTitle(screenMode)}>
            <b>Gong</b>
            <b>Check</b>과 함께 하세요.
          </h1>
        )}
      </div>
    </section>
  );
};

export default HostViewSection1;
