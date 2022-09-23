import UserPicture from '../UserPicture';
import useUserViewSection1 from './useUserViewSection1';

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

interface UserViewSection1 {
  screenMode: ScreenModeType;
}

const UserViewSection1: React.FC<UserViewSection1> = ({ screenMode }) => {
  const { eventNumber, sectionRef } = useUserViewSection1();

  return (
    <section css={styles.layout} ref={sectionRef}>
      <div css={styles.content}>
        <h1 css={styles.title}>쉽게 확인해요.</h1>
        <h1 css={styles.subTitle}>함께 사용할</h1>

        {eventNumber >= 1 && (
          <>
            <UserPicture image={mobileView1} css={styles.leftSection} />
            <h1 css={styles.leftSectionTitle}>
              <b>공간</b>과
            </h1>
          </>
        )}
        {eventNumber === 2 && (
          <>
            <UserPicture image={mobileView2} css={styles.rightSection} />
            <h1 css={styles.rightSectionTitle}>
              <b>업무</b>를
            </h1>
          </>
        )}
      </div>
    </section>
  );
};

export default UserViewSection1;
