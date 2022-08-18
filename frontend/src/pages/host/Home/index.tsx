import MainSection from './MainSection';
import { css } from '@emotion/react';
import { useEffect, useRef, useState } from 'react';

import GitHubLoginButton from '@/components/common/GitHubLoginButton';

import useLazyLoading from '@/hooks/useLazyLoading';
import useScroll from '@/hooks/useScroll';

import createSpace from '@/assets/createSpace.png';
import dashboard from '@/assets/dashboard.png';
import edit2 from '@/assets/edit2.png';
import edit from '@/assets/edit.png';
import homeCover from '@/assets/homeCover.png';
import mobileView1 from '@/assets/mobileView1.png';
import mobileView2 from '@/assets/mobileView2.png';
import mobileView3 from '@/assets/mobileView3.png';
import mobileView4 from '@/assets/mobileView4.png';
import mobileView5 from '@/assets/mobileView5.png';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const Home: React.FC = () => {
  return (
    <div
      css={css`
        width: 100vw;
      `}
    >
      <MainSection />
      <UserViewSection1 />
      <UserViewSection2 />
      <UserViewSection3 />
      <HostViewSection1 />
      <Footer />
    </div>
  );
};

const Footer = () => {
  return (
    <section
      css={css`
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100vw;
        height: 100vh;
        background-color: ${theme.colors.background};
        border: 2px solid black;
      `}
    />
  );
};

const UserViewSection1 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>(0.1);
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState(0);

  useEffect(() => {
    if (scrollPosition >= 40 && scrollPosition < 80) {
      setEventNumber(1);
      return;
    }
    if (scrollPosition >= 80) {
      setEventNumber(2);
      return;
    }
    setEventNumber(0);
  }, [scrollPosition]);

  return (
    <section
      css={css`
        width: 100vw;
        height: 100vh;
        z-index: 10;
      `}
      ref={sectionRef}
    >
      {isLoaded && (
        <div
          css={css`
            width: 100%;
            height: 100%;
            background-color: ${theme.colors.background};
            position: relative;
            z-index: 10;
          `}
        >
          {eventNumber !== 2 ? (
            <h1
              css={css`
                position: absolute;
                top: 18%;
                left: 12%;
                z-index: 11;
                font-size: 4vh;
                color: ${theme.colors.primary};
              `}
            >
              관리하는 공간을 볼 수 있어요.
            </h1>
          ) : (
            <h1
              css={css`
                position: absolute;
                top: 18%;
                left: 12%;
                z-index: 11;
                font-size: 4vh;
                color: ${theme.colors.green};
              `}
            >
              생성된 작업을 볼 수 있어요.
            </h1>
          )}
          {eventNumber >= 1 && (
            <img
              css={css`
                position: absolute;
                top: 5%;
                right: 25%;
                height: 75%;
                animation: ${animation.moveRight} 1.5s;
                animation-fill-mode: forwards;
                z-index: 11;
              `}
              src={mobileView1}
              alt=""
            />
          )}
          {eventNumber === 2 && (
            <img
              css={css`
                position: absolute;
                top: 15%;
                right: 12%;
                height: 75%;
                animation: ${animation.moveLeft} 1.5s;
                animation-fill-mode: forwards;
                z-index: 12;
              `}
              src={mobileView2}
              alt=""
            />
          )}
        </div>
      )}
    </section>
  );
};

const UserViewSection2 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>(0.1);
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState(0);

  useEffect(() => {
    if (scrollPosition >= 140 && scrollPosition < 180) {
      setEventNumber(1);
      return;
    }
    if (scrollPosition >= 180) {
      setEventNumber(2);
      return;
    }
    setEventNumber(0);
  }, [scrollPosition]);

  return (
    <section
      css={css`
        width: 100vw;
        height: 100vh;
      `}
      ref={sectionRef}
    >
      {isLoaded && (
        <div
          css={css`
            width: 100%;
            height: 100%;
            background-color: ${theme.colors.background};
            position: relative;
          `}
        >
          {eventNumber !== 2 ? (
            <h1
              css={css`
                position: absolute;
                top: 18%;
                right: 12%;
                z-index: 11;
                font-size: 4vh;
                color: ${theme.colors.primary};
              `}
            >
              체크리스트를 볼 수 있어요.
            </h1>
          ) : (
            <h1
              css={css`
                position: absolute;
                top: 18%;
                right: 6%;
                z-index: 11;
                font-size: 4vh;
                color: ${theme.colors.green};
              `}
            >
              공간 및 작업 정보를 확인할 수 있어요.
            </h1>
          )}
          {eventNumber === 1 && (
            <img
              css={css`
                position: absolute;
                top: 11%;
                left: 20%;
                height: 75%;
                animation: ${animation.moveUp} 1.5s;
                animation-fill-mode: forwards;
                z-index: 11;
              `}
              src={mobileView3}
              alt=""
            />
          )}
          {eventNumber === 2 && (
            <img
              css={css`
                position: absolute;
                top: 11%;
                left: 20%;
                height: 75%;
                z-index: 12;
              `}
              src={mobileView4}
              alt=""
            />
          )}
        </div>
      )}
    </section>
  );
};

const UserViewSection3 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>(0.1);
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState(0);

  console.log(scrollPosition);
  useEffect(() => {
    if (scrollPosition >= 240 && scrollPosition < 280) {
      setEventNumber(1);
      return;
    }
    if (scrollPosition >= 280) {
      setEventNumber(2);
      return;
    }
    setEventNumber(0);
  }, [scrollPosition]);

  return (
    <section
      css={css`
        width: 100vw;
        height: 100vh;
      `}
      ref={sectionRef}
    >
      {isLoaded && (
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
              top: 8%;
              left: 25%;
              height: 75%;
              z-index: 11;
              font-size: 4vh;
              color: ${theme.colors.primary};
            `}
          >
            여러명의 유저가 실시간으로 사용할 수 있어요.
          </h1>

          {eventNumber === 1 && (
            <>
              <img
                css={css`
                  position: absolute;
                  top: 24%;
                  left: 12%;
                  height: 68%;
                  animation: ${animation.moveRight} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                `}
                src={mobileView3}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 24%;
                  right: 12%;
                  height: 68%;
                  animation: ${animation.moveLeft} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                `}
                src={mobileView3}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 24%;
                  left: 42%;
                  height: 68%;
                  z-index: 112;
                  animation: ${animation.fadeIn} 1.5s;
                  animation-fill-mode: forwards;
                `}
                src={mobileView3}
                alt=""
              />
            </>
          )}
          {eventNumber === 2 && (
            <>
              <img
                css={css`
                  position: absolute;
                  top: 24%;
                  left: 12%;
                  height: 68%;
                  z-index: 1;
                `}
                src={mobileView5}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 24%;
                  right: 12%;
                  height: 68%;
                  z-index: 112;
                `}
                src={mobileView5}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 24%;
                  left: 42%;
                  height: 68%;
                  z-index: 112;
                `}
                src={mobileView5}
                alt=""
              />
            </>
          )}
        </div>
      )}
    </section>
  );
};

const HostViewSection1 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>(0.1);
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState(0);

  console.log(scrollPosition);
  useEffect(() => {
    if (scrollPosition >= 340) {
      setEventNumber(1);
      return;
    }
    setEventNumber(0);
  }, [scrollPosition]);

  return (
    <section
      css={css`
        width: 100vw;
        height: 100vh;
      `}
      ref={sectionRef}
    >
      {isLoaded && (
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

              height: 75%;
              z-index: 11;
              font-size: 4vh;
              color: ${theme.colors.primary};
              top: 25%;
              left: 50%;
              transform: translate(-50%, -25%);
            `}
          >
            공간을 생성할 수 있어요.
          </h1>

          {eventNumber === 1 && (
            <img
              css={css`
                position: absolute;
                top: 24%;
                left: 16%;
                height: 68%;
                animation: ${animation.moveUp} 1.5s;
                animation-fill-mode: forwards;
                z-index: 11;
              `}
              src={createSpace}
              alt=""
            />
          )}
        </div>
      )}
    </section>
  );
};

export default Home;
