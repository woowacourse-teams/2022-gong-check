import MainSection from './MainSection';
import { css } from '@emotion/react';
import React, { RefObject, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';
import useResizeScreen from '@/hooks/useResizeScreen';

import { ScreenModeType } from '@/types';

import screenSize from '@/constants/screenSize';

import createSpace_280w from '@/assets/createSpace-280w.webp';
import createSpace_360w from '@/assets/createSpace-360w.webp';
import createSpace_540w from '@/assets/createSpace-540w.webp';
import createSpace_800w from '@/assets/createSpace-800w.webp';
import createSpace_fallback from '@/assets/createSpace-fallback.jpg';
// import createSpace from '@/assets/createSpace.png';
import dashboard_280w from '@/assets/dashboard-280w.webp';
import dashboard_360w from '@/assets/dashboard-360w.webp';
import dashboard_540w from '@/assets/dashboard-540w.webp';
import dashboard_800w from '@/assets/dashboard-800w.webp';
import dashboard_fallback from '@/assets/dashboard-fallback.jpg';
// import dashboard from '@/assets/dashboard.png';
import edit2_280w from '@/assets/edit2-280w.webp';
import edit2_360w from '@/assets/edit2-360w.webp';
import edit2_540w from '@/assets/edit2-540w.webp';
import edit2_800w from '@/assets/edit2-800w.webp';
import edit2_fallback from '@/assets/edit2-fallback.jpg';
// import edit2 from '@/assets/edit2.png';
import edit_280w from '@/assets/edit-280w.webp';
import edit_360w from '@/assets/edit-360w.webp';
import edit_540w from '@/assets/edit-540w.webp';
import edit_800w from '@/assets/edit-800w.webp';
import edit_fallback from '@/assets/edit-fallback.jpg';
// import edit from '@/assets/edit.png';
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

type UserImageType = {
  '160w': string;
  '240w': string;
  '320w': string;
  '480w': string;
  fallback: string;
};

type HostImageType = {
  '280w': string;
  '360w': string;
  '540w': string;
  '800w': string;
  fallback: string;
};

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

const createSpace: HostImageType = {
  '280w': createSpace_280w,
  '360w': createSpace_360w,
  '540w': createSpace_540w,
  '800w': createSpace_800w,
  fallback: createSpace_fallback,
};

const dashboard: HostImageType = {
  '280w': dashboard_280w,
  '360w': dashboard_360w,
  '540w': dashboard_540w,
  '800w': dashboard_800w,
  fallback: dashboard_fallback,
};

const edit2: HostImageType = {
  '280w': edit2_280w,
  '360w': edit2_360w,
  '540w': edit2_540w,
  '800w': edit2_800w,
  fallback: edit2_fallback,
};

const edit: HostImageType = {
  '280w': edit_280w,
  '360w': edit_360w,
  '540w': edit_540w,
  '800w': edit_800w,
  fallback: edit_fallback,
};

const UserPicture: React.FC<{ image: UserImageType; className?: string }> = ({ image, className }) => {
  return (
    <picture className={className}>
      <source media={`(max-width: ${screenSize.TABLET}px)`} type="image/webp" srcSet={image['160w']} />
      <source media={`(max-width: ${screenSize.DESKTOP}px)`} type="image/webp" srcSet={image['240w']} />
      <source media={`(max-width: ${screenSize.DESKTOP_BIC}px)`} type="image/webp" srcSet={image['320w']} />
      <source type="image/webp" srcSet={image['480w']} />
      <img src={image.fallback} alt="" />
    </picture>
  );
};

const HostPicture: React.FC<{ image: HostImageType; className?: string }> = ({ image, className }) => {
  return (
    <picture className={className}>
      <source media={`(max-width: ${screenSize.DESKTOP}px)`} type="image/webp" srcSet={image['280w']} />
      <source media={`(max-width: ${screenSize.DESKTOP_BIC}px)`} type="image/webp" srcSet={image['360w']} />
      <source type="image/webp" srcSet={image['540w']} />
      <img src={image.fallback} alt="" />
    </picture>
  );
};

const FloatingActionButton: React.FC<{ mainRef: RefObject<HTMLElement> }> = ({ mainRef }) => {
  const navigate = useNavigate();
  const [eventNumber, setEventNumber] = useState(0);

  const { scrollInfo } = useOnContainerScroll(mainRef, () => {
    if (scrollInfo.progress === 1) {
      setEventNumber(1);
      return;
    }

    setEventNumber(0);
  });

  const onClick = () => {
    navigate('/host');
  };

  return (
    <div>
      <div
        css={css`
          ${eventNumber === 1 &&
          `
            transform: translateX(calc(-50vw + 114px))  scale(1.4);
            transition-duration: 1.5s;
            `}

          right: 40px;
          bottom: 40px;
          position: fixed;
          z-index: 200;
          cursor: pointer;
          color: white;
          font-weight: 600;
          background-color: ${theme.colors.primary};
          padding: 16px 32px;
          border-radius: 24px;
          box-shadow: 0px 0px 2px 3px ${theme.colors.shadow10};
        `}
        onClick={onClick}
      >
        <span>시작하기</span>
      </div>
    </div>
  );
};

const MainPage: React.FC = () => {
  const mainRef = useRef<HTMLDivElement>(null);

  const { screenMode } = useResizeScreen();

  return (
    <div
      css={css`
        width: 100vw;
        background-color: ${theme.colors.background};
      `}
      ref={mainRef}
    >
      <MainSection />
      <UserViewSection1 screenMode={screenMode} />
      <UserViewSection2 screenMode={screenMode} />
      <UserViewSection3 screenMode={screenMode} />
      <HostViewSection1 screenMode={screenMode} />
      <HostViewSection2 screenMode={screenMode} />
      <HostViewSection3 screenMode={screenMode} />
      <FloatingActionButton mainRef={mainRef} />
    </div>
  );
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
    <section
      css={css`
        width: 100vw;
        height: ${screenMode === 'DESKTOP' ? `100vh` : `50vh`};
      `}
      ref={sectionRef}
    >
      <div
        css={css`
          width: 100%;
          height: 100%;
          position: relative;
          background-color: ${theme.colors.background};
        `}
      >
        <h1
          css={css`
            top: 21.4vw;
            left: 50%;
            transform: translateX(-50%);
            z-index: 11;
            font-size: ${screenMode === 'DESKTOP' ? `2.7vw` : `5vw`};
            white-space: nowrap;
            color: ${theme.colors.gray800};
            position: absolute;
            b {
              color: ${theme.colors.primary};
            }

            b + b {
              color: ${theme.colors.green};
            }
          `}
        >
          직접 내 공간을 <b>생성</b>하고 <b>관리</b>하고싶다면?
        </h1>
        {eventNumber === 1 && (
          <h1
            css={css`
              position: absolute;
              top: 44vw;
              z-index: 11;
              color: ${theme.colors.gray800};
              animation: ${animation.fadeIn} 1.5s;
              white-space: nowrap;

              ${screenMode === 'DESKTOP'
                ? `left: 33%; font-size: 2.7vw;`
                : `left: 50%; transform: translateX(-50%); font-size: 5vw;`}

              b {
                color: ${theme.colors.primary};
              }

              b + b {
                color: ${theme.colors.green};
              }
            `}
          >
            <b>Gong</b>
            <b>Check</b>과 함께 하세요.
          </h1>
        )}
      </div>
    </section>
  );
};

const HostViewSection2 = ({ screenMode }: { screenMode: ScreenModeType }) => {
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
        margin-top: 10vh;
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
          display: flex;
          justify-content: center;
        `}
      >
        <h1
          css={css`
            position: absolute;
            top: 2vw;
            left: 50%;
            transform: translateX(-50%);
            z-index: 11;
            font-size: ${screenMode === 'DESKTOP' ? `2.7vw` : `5vw`};
            color: ${theme.colors.gray800};
            animation: ${animation.fadeIn} 1.5s;
            animation-fill-mode: forwards;
          `}
        >
          내 공간 관리
        </h1>
        {eventNumber >= 1 && (
          <div
            css={css`
              display: grid;
              position: absolute;
              top: 14vw;
              animation: ${animation.moveUp} 1.5s;
              animation-fill-mode: forwards;
              z-index: 11;
              gap: 10px 20px;
              grid-template-columns: repeat(2, 1fr);

              @media (max-width: 768px) {
                grid-template-columns: repeat(1, 1fr);
                row-gap: 20px;
              }

              & img {
                height: 100%;
                width: 100%;
              }
            `}
          >
            <HostPicture
              image={createSpace}
              css={css`
                box-shadow: 3px 3px 3px 3px ${theme.colors.shadow20};
                border-radius: 4px;
                border: 4px solid ${theme.colors.primary};
              `}
            />
            <HostPicture
              image={dashboard}
              css={css`
                box-shadow: 3px 3px 3px 3px ${theme.colors.shadow20};
                border-radius: 4px;
                border: 4px solid ${theme.colors.primary};
              `}
            />
            <HostPicture
              image={edit}
              css={css`
                box-shadow: 3px 3px 3px 3px ${theme.colors.shadow20};
                border-radius: 4px;
                border: 6px solid ${theme.colors.green};
              `}
            />
            <HostPicture
              image={edit2}
              css={css`
                box-shadow: 3px 3px 3px 3px ${theme.colors.shadow20};
                border-radius: 4px;
                border: 4px solid ${theme.colors.green};
              `}
            />
          </div>
        )}
      </div>
    </section>
  );
};

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
    <section
      css={css`
        width: 100vw;
        height: 42vh;
      `}
      ref={sectionRef}
    >
      <div
        css={css`
          width: 100%;
          height: 100%;
          background-color: ${theme.colors.background};
          display: flex;
          align-items: center;
          justify-content: center;
        `}
      >
        {eventNumber === 1 && (
          <h1
            css={css`
              z-index: 11;
              font-size: ${screenMode === 'DESKTOP' ? `2.7vw` : `5vw`};
              color: ${theme.colors.gray800};
              animation: ${animation.moveDown} 1.5s;
              animation-fill-mode: forwards;
              padding-bottom: 8vh;
            `}
          >
            지금 바로
          </h1>
        )}
      </div>
    </section>
  );
};

export default MainPage;
