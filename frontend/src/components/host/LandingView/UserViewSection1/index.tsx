import UserPicture from '../UserPicture';
import { css } from '@emotion/react';
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

import animation from '@/styles/animation';
import theme from '@/styles/theme';

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
    <section
      css={css`
        width: 100vw;
        height: 100vh;
        z-index: 10;
      `}
      ref={sectionRef}
    >
      <div
        css={css`
          width: 100%;
          height: 100%;
          background-color: ${theme.colors.background};
          position: relative;
          z-index: 10;
        `}
      >
        <h1
          css={css`
            position: absolute;
            color: ${theme.colors.primary};
            z-index: 11;
            top: 13vw;
            ${screenMode === 'DESKTOP'
              ? `left: 12%; font-size: 4.1vw;`
              : `left: 50%; transform: translateX(-50%); font-size: 6vw;`}
          `}
        >
          쉽게 확인해요.
        </h1>
        <h1
          css={css`
            position: absolute;
            top: 29vw;
            z-index: 11;
            color: ${theme.colors.gray800};
            ${screenMode === 'DESKTOP'
              ? `left: 10%; font-size: 2.7vw;`
              : `left: 30%; transform: translateX(-50%); font-size: 3.7vw;`}
          `}
        >
          함께 사용할
        </h1>

        {eventNumber >= 1 && (
          <div
            css={css`
              animation: ${animation.moveRight} 1.5s;
              animation-fill-mode: forwards;
              z-index: 11;
              position: absolute;
              height: 100%;
              width: 100%;
            `}
          >
            <UserPicture
              image={mobileView1}
              css={css`
                position: absolute;
                ${screenMode === 'DESKTOP' ? `top: 5vw; right: 25%; width: 20%;` : `top: 28%; left: 20%;`}
              `}
            />
            <h1
              css={css`
                position: absolute;
                top: 29vw;
                animation: ${animation.moveDown} 1.5s;
                animation-fill-mode: forwards;
                color: ${theme.colors.gray800};
                z-index: 11;
                ${screenMode === 'DESKTOP'
                  ? `font-size: 2.7vw; left: 24.5%;`
                  : `font-size: 3.7vw; left: 42%; transform: translateX(-50%);`}

                b {
                  color: ${theme.colors.green};
                }
              `}
            >
              <b>공간</b>과
            </h1>
          </div>
        )}
        {eventNumber === 2 && (
          <div
            css={css`
              animation: ${animation.moveLeft} 1.5s;
              animation-fill-mode: forwards;
              z-index: 12;
              position: absolute;
              height: 100%;
              width: 100%;
            `}
          >
            <UserPicture
              image={mobileView2}
              css={css`
                position: absolute;
                ${screenMode === 'DESKTOP' ? `top: 12vw; right: 10%; width: 20%;` : `top: 32%; right: 20%;`}
              `}
            />
            <h1
              css={css`
                position: absolute;
                top: 29vw;
                z-index: 11;
                animation: ${animation.moveUp} 1.5s;
                animation-fill-mode: forwards;
                color: ${theme.colors.gray800};
                ${screenMode === 'DESKTOP'
                  ? `font-size: 2.7vw; left: 33%;`
                  : `font-size: 3.7vw; left: 54%; transform: translateX(-50%);`}

                b {
                  color: ${theme.colors.green};
                }
              `}
            >
              <b>업무</b>를
            </h1>
          </div>
        )}
      </div>
    </section>
  );
};

export default UserViewSection1;
