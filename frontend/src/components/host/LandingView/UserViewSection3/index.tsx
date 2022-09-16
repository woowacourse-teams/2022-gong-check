import UserPicture from '../UserPicture';
import { css } from '@emotion/react';
import { useRef, useState } from 'react';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

import { ScreenModeType, UserImageType } from '@/types';

import mobileView3_160w from '@/assets/mobileView3-160w.webp';
import mobileView3_240w from '@/assets/mobileView3-240w.webp';
import mobileView3_320w from '@/assets/mobileView3-320w.webp';
import mobileView3_480w from '@/assets/mobileView3-480w.webp';
import mobileView3_fallback from '@/assets/mobileView3-fallback.png';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const mobileView3: UserImageType = {
  '160w': mobileView3_160w,
  '240w': mobileView3_240w,
  '320w': mobileView3_320w,
  '480w': mobileView3_480w,
  fallback: mobileView3_fallback,
};

const UserViewSection3 = ({ screenMode }: { screenMode: ScreenModeType }) => {
  const [eventNumber, setEventNumber] = useState(0);
  const sectionRef = useRef<HTMLDivElement>(null);

  const { dimension, scrollInfo } = useOnContainerScroll(sectionRef, () => {
    const progress = Math.min(Math.max(0, scrollInfo.scrollY / dimension.windowHeight), 1) * 100;
    if (progress === 0) return;

    if (progress >= 50) {
      setEventNumber(1);
      return;
    }
    setEventNumber(0);
  });

  return (
    <section
      css={css`
        width: 100vw;
        height: 100vh;
      `}
      ref={sectionRef}
    >
      <div
        css={css`
          width: 100%;
          height: 100%;
          background-color: ${theme.colors.background};
          position: relative;
        `}
      >
        <h1
          css={css`
            position: absolute;
            top: 2.2vw;
            z-index: 11;
            color: ${theme.colors.primary};
            ${screenMode === 'DESKTOP'
              ? `left: 38.5%; font-size: 4.1vw;`
              : `left: 50%; transform: translateX(-50%); font-size: 6vw;`}
          `}
        >
          함께 사용해요.
        </h1>
        {eventNumber === 1 && (
          <div>
            <UserPicture
              image={mobileView3}
              css={css`
                position: absolute;
                z-index: 11;
                animation: ${animation.moveRight} 1.5s;
                animation-fill-mode: forwards;
                ${screenMode === 'DESKTOP' ? `top: 15vw; left: 26%; width: 18%;` : `top: 20%; left: 20%;`}
              `}
            />
            <UserPicture
              image={mobileView3}
              css={css`
                position: absolute;
                z-index: 11;
                animation: ${animation.moveLeft} 1.5s;
                animation-fill-mode: forwards;
                ${screenMode === 'DESKTOP' ? `top: 15vw; right: 26%; width: 18%;` : `top: 28%; right: 20%;`}
              `}
            />

            <h1
              css={css`
                position: absolute;
                z-index: 11;
                color: ${theme.colors.gray800};
                animation: ${animation.fadeIn} 1.5s;
                animation-fill-mode: forwards;
                ${screenMode === 'DESKTOP'
                  ? `top: 10.5vw; left: 40.4%; font-size: 1.3vw;`
                  : `top: 16vw; left: 50%; transform: translateX(-50%); font-size: 3vw;`}

                b {
                  color: ${theme.colors.green};
                }
              `}
            >
              여러명이 <b>동시에</b> 같은 업무 체크 가능
            </h1>
          </div>
        )}
      </div>
    </section>
  );
};

export default UserViewSection3;
