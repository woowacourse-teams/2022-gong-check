import UserPicture from '../UserPicture';
import { useRef, useState } from 'react';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

import { ScreenModeType, UserImageType } from '@/types';

import mobileView1_160w from '@/assets/mobileView1-160w.webp';
import mobileView1_240w from '@/assets/mobileView1-240w.webp';
import mobileView1_320w from '@/assets/mobileView1-320w.webp';
import mobileView1_480w from '@/assets/mobileView1-480w.webp';
import mobileView1_fallback from '@/assets/mobileView1-fallback.png';
import mobileView2_160w from '@/assets/mobileView2-160w.webp';
import mobileView2_240w from '@/assets/mobileView2-240w.webp';
import mobileView2_320w from '@/assets/mobileView2-320w.webp';
import mobileView2_480w from '@/assets/mobileView2-480w.webp';
import mobileView2_fallback from '@/assets/mobileView2-fallback.png';

import styles from './styles';

const mobileView1: UserImageType = {
  '160w': mobileView1_160w,
  '240w': mobileView1_240w,
  '320w': mobileView1_320w,
  '480w': mobileView1_480w,
  fallback: mobileView1_fallback,
};

const mobileView2: UserImageType = {
  '160w': mobileView2_160w,
  '240w': mobileView2_240w,
  '320w': mobileView2_320w,
  '480w': mobileView2_480w,
  fallback: mobileView2_fallback,
};

const UserViewSection1 = ({ screenMode }: { screenMode: ScreenModeType }) => {
  const [eventNumber, setEventNumber] = useState(0);
  const sectionRef = useRef<HTMLDivElement>(null);

  const { dimension, scrollInfo } = useOnContainerScroll(sectionRef, () => {
    const progress = Math.min(Math.max(0, scrollInfo.scrollY / dimension.windowHeight), 1) * 100;
    if (progress === 0) return;

    if (progress >= 40 && progress < 80) {
      setEventNumber(1);
      return;
    }
    if (progress >= 80) {
      setEventNumber(2);
      return;
    }
    setEventNumber(0);
  });

  return (
    <section css={styles.layout} ref={sectionRef}>
      <div css={styles.content}>
        <h1 css={styles.title(screenMode)}>쉽게 확인해요.</h1>
        <h1 css={styles.subTitle(screenMode)}>함께 사용할</h1>

        {eventNumber >= 1 && (
          <div css={styles.leftSectionWrapper}>
            <UserPicture image={mobileView1} css={styles.leftSection(screenMode)} />
            <h1 css={styles.leftSectionTitle(screenMode)}>
              <b>공간</b>과
            </h1>
          </div>
        )}
        {eventNumber === 2 && (
          <div css={styles.rightSectionWrapper}>
            <UserPicture image={mobileView2} css={styles.rightSection(screenMode)} />
            <h1 css={styles.rightSectionTitle(screenMode)}>
              <b>업무</b>를
            </h1>
          </div>
        )}
      </div>
    </section>
  );
};

export default UserViewSection1;
