import UserPicture from '../UserPicture';
import useUserViewSection3 from './useUserViewSection3';
import { UserImageType } from 'gongcheck-util-types/types/image';

import mobileView3_160w from '@/assets/mobileView3-160w.webp';
import mobileView3_240w from '@/assets/mobileView3-240w.webp';
import mobileView3_320w from '@/assets/mobileView3-320w.webp';
import mobileView3_480w from '@/assets/mobileView3-480w.webp';
import mobileView3_fallback from '@/assets/mobileView3-fallback.png';

import styles from './styles';

const mobileView3: UserImageType = {
  '160w': mobileView3_160w,
  '240w': mobileView3_240w,
  '320w': mobileView3_320w,
  '480w': mobileView3_480w,
  fallback: mobileView3_fallback,
};

const UserViewSection3: React.FC = () => {
  const { sectionRef, eventNumber } = useUserViewSection3();

  return (
    <section css={styles.layout} ref={sectionRef}>
      <div css={styles.content}>
        <h1 css={styles.title}>함께 사용해요.</h1>
        {eventNumber === 1 && (
          <>
            <UserPicture image={mobileView3} css={styles.leftSection} />
            <UserPicture image={mobileView3} css={styles.rightSection} />
            <h1 css={styles.subTitle}>
              여러명이 <b>동시에</b> 같은 업무 체크 가능
            </h1>
          </>
        )}
      </div>
    </section>
  );
};

export default UserViewSection3;
