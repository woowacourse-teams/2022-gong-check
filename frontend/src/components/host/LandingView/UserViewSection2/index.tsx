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
import mobileView4_160w from '@/assets/mobileView4-160w.webp';
import mobileView4_240w from '@/assets/mobileView4-240w.webp';
import mobileView4_320w from '@/assets/mobileView4-320w.webp';
import mobileView4_480w from '@/assets/mobileView4-480w.webp';
import mobileView4_fallback from '@/assets/mobileView4-fallback.png';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

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

const UserViewSection2 = ({ screenMode }: { screenMode: ScreenModeType }) => {
  const sectionRef = useRef<HTMLDivElement>(null);
  const [eventNumber, setEventNumber] = useState(0);

  const { dimension, scrollInfo } = useOnContainerScroll(sectionRef, () => {
    const progress = Math.min(Math.max(0, scrollInfo.scrollY / dimension.windowHeight), 1) * 100;
    if (progress === 0) return;

    if (progress >= 60 && progress < 100) {
      setEventNumber(1);
      return;
    }
    if (progress >= 100) {
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
            top: 12vw;
            z-index: 11;
            color: ${theme.colors.green};
            ${screenMode === 'DESKTOP'
              ? `right: 10%; font-size: 4.1vw;`
              : `left: 50%; transform: translateX(-50%); font-size: 6vw;`}
          `}
        >
          간단하게 체크해요.
        </h1>
        {eventNumber >= 1 && (
          <div
            css={css`
              animation: ${animation.moveDown} 1.5s;
              animation-fill-mode: forwards;
              z-index: 11;
              position: absolute;
              height: 100%;
              width: 100%;
            `}
          >
            <UserPicture
              image={mobileView3}
              css={css`
                position: absolute;
                ${screenMode === 'DESKTOP' ? `top: 4vw; left: 28%; width: 20%;` : `top: 28%; left: 20%;`}
              `}
            />
            <h1
              css={css`
                position: absolute;
                top: 28.5vw;
                z-index: 11;
                color: ${theme.colors.gray800};
                animation: ${animation.moveRight} 1.5s;
                animation-fill-mode: forwards;
                ${screenMode === 'DESKTOP' ? `right: 26%; font-size: 2.7vw;` : `right: 38%; font-size: 3.7vw;`}

                b {
                  color: ${theme.colors.primary};
                }
              `}
            >
              <b>체크리스트</b>와
            </h1>
          </div>
        )}
        {eventNumber === 2 && (
          <div
            css={css`
              position: absolute;
              z-index: 12;
              animation: ${animation.moveUp} 1.5s;
              animation-fill-mode: forwards;
              height: 100%;
              width: 100%;
            `}
          >
            <UserPicture
              image={mobileView4}
              css={css`
                position: absolute;
                ${screenMode === 'DESKTOP' ? `top: 7vw; left: 6%; width: 20%;` : `top: 32%; right: 20%;`}
              `}
            />
            <h1
              css={css`
                position: absolute;
                top: 28.5vw;
                z-index: 11;
                color: ${theme.colors.gray800};
                animation: ${animation.moveLeft} 1.5s;
                animation-fill-mode: forwards;
                ${screenMode === 'DESKTOP' ? `right: 9%; font-size: 2.7vw;` : `right: 13%; font-size: 3.7vw;`}
                b {
                  color: ${theme.colors.primary};
                }
              `}
            >
              <b>상세정보</b> 제공
            </h1>
            {screenMode === 'DESKTOP' && (
              <h1
                css={css`
                  position: absolute;
                  top: 28.5vw;
                  right: 17%;
                  z-index: 11;
                  font-size: 1vw;
                  color: ${theme.colors.gray500};
                  animation: ${animation.moveLeft} 1.5s;
                  animation-fill-mode: forwards;
                `}
              >
                How, Where
              </h1>
            )}
          </div>
        )}
      </div>
    </section>
  );
};

export default UserViewSection2;
