import UserPicture from '../UserPicture';
import useUserViewSection2 from './useUserViewSection2';
import { UserImageType } from 'gongcheck-util-types/image';

import mobileView3_160w from '@/assets/mobileView3-160w.webp';
import mobileView3_240w from '@/assets/mobileView3-240w.webp';
import mobileView3_320w from '@/assets/mobileView3-320w.webp';
import mobileView3_480w from '@/assets/mobileView3-480w.webp';
import mobileView3_fallback from '@/assets/mobileView3-fallback.png';
import mobileView4_160w from '@/assets/mobileView4-160w.webp';
import mobileView4_240w from '@/assets/mobileView4-240w.webp';
import mobileView4_320w from '@/assets/mobileView4-320w.webp';
import mobileView4_480w from '@/assets/mobileView4-480w.webp';
import mobileView4_fallback from '@/assets/mobileView4-fallback.png';

import styles from './styles';

const mobileView3: UserImageType = {
  '160w': mobileView3_160w,
  '240w': mobileView3_240w,
  '320w': mobileView3_320w,
  '480w': mobileView3_480w,
  fallback: mobileView3_fallback,
};

const mobileView4: UserImageType = {
  '160w': mobileView4_160w,
  '240w': mobileView4_240w,
  '320w': mobileView4_320w,
  '480w': mobileView4_480w,
  fallback: mobileView4_fallback,
};

const UserViewSection2: React.FC = () => {
  const { sectionRef, eventNumber } = useUserViewSection2();

  return (
    <section css={styles.layout} ref={sectionRef}>
      <div css={styles.content}>
        <h1 css={styles.title}>간단하게 체크해요.</h1>
        {eventNumber >= 1 && (
          <>
            <UserPicture image={mobileView3} css={styles.leftSection} />
            <h1 css={styles.leftSectionTitle}>
              <b>체크리스트</b>와
            </h1>
          </>
        )}
        {eventNumber === 2 && (
          <>
            <UserPicture image={mobileView4} css={styles.rightSection} />
            <h1 css={styles.rightSectionTitle}>
              <b>상세정보</b> 제공
            </h1>
          </>
        )}
      </div>
    </section>
  );
};

export default UserViewSection2;
