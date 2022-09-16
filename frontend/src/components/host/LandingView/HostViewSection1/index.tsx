import useHostViewSection1 from './useHostViewSection1';

import { ScreenModeType } from '@/types';

import styles from './styles';

const HostViewSection1 = ({ screenMode }: { screenMode: ScreenModeType }) => {
  const { eventNumber, sectionRef } = useHostViewSection1();

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
