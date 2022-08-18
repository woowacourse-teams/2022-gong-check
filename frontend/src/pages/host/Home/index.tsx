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

const FloatingActionButton: React.FC = () => {
  const [isShow, setIsShow] = useState(false);
  const onClick = () => {
    setIsShow(prev => !prev);
  };

  return (
    <div>
      <div
        css={css`
          position: fixed;
          right: 40px;
          bottom: 40px;
          z-index: 200;
          cursor: pointer;
          color: white;
          font-weight: 600;
          background-color: ${theme.colors.primary};
          padding: 16px;
          border-radius: 24px;
          box-shadow: 2px 2px 2px 2px ${theme.colors.shadow10};
          &:hover {
            animation: ${animation.littleShake} 2s 0s infinite;
          }
        `}
        onClick={onClick}
      >
        <span>지금 바로 시작하기</span>
      </div>
      {isShow && (
        <div
          css={css`
            position: fixed;
            right: 40px;
            bottom: 50px;
            z-index: 200;
            cursor: pointer;
            animation: ${animation.customMoveUp('20px')} 2s 0s;
          `}
        >
          <div
            css={css`
              position: absolute;
              right: 0;
              bottom: 50px;
            `}
          >
            <div
              css={css`
                display: flex;
                flex-direction: column;
                gap: 10px;
              `}
            >
              <GitHubLoginButton />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

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
      <HostViewSection2 />
      <HostViewSection3 />
      <FloatingActionButton />
    </div>
  );
};

const UserViewSection1 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>();
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
          <h1
            css={css`
              position: absolute;
              top: 24%;
              left: 12%;
              z-index: 11;
              font-size: 7.5vh;
              color: ${theme.colors.primary};
            `}
          >
            쉽게 확인해요.
          </h1>
          <h1
            css={css`
              position: absolute;
              top: 52%;
              left: 10%;
              z-index: 11;
              font-size: 5vh;
              color: ${theme.colors.gray800};
            `}
          >
            함께 사용할
          </h1>
          {eventNumber >= 1 && (
            <>
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
              <h1
                css={css`
                  position: absolute;
                  top: 52%;
                  left: 24.5%;
                  z-index: 11;
                  font-size: 5vh;
                  animation: ${animation.moveDown} 1.5s;
                  animation-fill-mode: forwards;
                  color: ${theme.colors.gray800};

                  b {
                    color: ${theme.colors.green};
                  }
                `}
              >
                <b>공간</b>과
              </h1>
            </>
          )}
          {eventNumber === 2 && (
            <>
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
              <h1
                css={css`
                  position: absolute;
                  top: 52%;
                  left: 33%;
                  z-index: 11;
                  font-size: 5vh;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                  color: ${theme.colors.gray800};

                  b {
                    color: ${theme.colors.green};
                  }
                `}
              >
                <b>업무</b>를
              </h1>
            </>
          )}
        </div>
      )}
    </section>
  );
};

const UserViewSection2 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>();
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
          <h1
            css={css`
              position: absolute;
              top: 24%;
              right: 12%;
              z-index: 11;
              font-size: 7.5vh;
              color: ${theme.colors.green};
            `}
          >
            간단하게 체크해요.
          </h1>
          {eventNumber >= 1 && (
            <>
              <img
                css={css`
                  position: absolute;
                  top: 8%;
                  left: 28%;
                  height: 75%;
                  animation: ${animation.moveDown} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                `}
                src={mobileView3}
                alt=""
              />
              <h1
                css={css`
                  position: absolute;
                  top: 52%;
                  right: 30%;
                  z-index: 11;
                  font-size: 5vh;
                  color: ${theme.colors.gray800};
                  animation: ${animation.moveRight} 1.5s;
                  animation-fill-mode: forwards;

                  b {
                    color: ${theme.colors.primary};
                  }
                `}
              >
                <b>체크리스트</b>와
              </h1>
            </>
          )}
          {eventNumber === 2 && (
            <>
              <img
                css={css`
                  position: absolute;
                  top: 14%;
                  left: 8%;
                  height: 75%;
                  z-index: 12;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                `}
                src={mobileView4}
                alt=""
              />
              <h1
                css={css`
                  position: absolute;
                  top: 52%;
                  right: 13%;
                  z-index: 11;
                  font-size: 5vh;
                  color: ${theme.colors.gray800};
                  animation: ${animation.moveLeft} 1.5s;
                  animation-fill-mode: forwards;
                  b {
                    color: ${theme.colors.primary};
                  }
                `}
              >
                <b>상세정보</b> 제공
              </h1>
              <h1
                css={css`
                  position: absolute;
                  top: 51.6%;
                  right: 21%;
                  z-index: 11;
                  font-size: 1.8vh;
                  color: ${theme.colors.gray500};
                  animation: ${animation.moveLeft} 1.5s;
                  animation-fill-mode: forwards;
                `}
              >
                How, Where
              </h1>
            </>
          )}
        </div>
      )}
    </section>
  );
};

const UserViewSection3 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>();
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState(0);

  useEffect(() => {
    if (scrollPosition >= 240) {
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
              top: 4%;
              left: 38.5%;
              height: 75%;
              z-index: 11;
              font-size: 7.5vh;
              color: ${theme.colors.primary};
            `}
          >
            함께 사용해요.
          </h1>
          {eventNumber === 1 && (
            <>
              <img
                css={css`
                  position: absolute;
                  top: 28%;
                  left: 26%;
                  height: 72%;
                  z-index: 11;
                  animation: ${animation.moveRight} 1.5s;
                  animation-fill-mode: forwards;
                `}
                src={mobileView3}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 28%;
                  right: 26%;
                  height: 72%;
                  z-index: 11;
                  animation: ${animation.moveLeft} 1.5s;
                  animation-fill-mode: forwards;
                `}
                src={mobileView3}
                alt=""
              />
              <h1
                css={css`
                  position: absolute;
                  top: 19.2%;
                  left: 41%;
                  height: 75%;
                  z-index: 11;
                  font-size: 2.4vh;
                  color: ${theme.colors.gray800};
                `}
              >
                여러명이 동시에 체크할 수 있어요.
              </h1>
            </>
          )}
        </div>
      )}
    </section>
  );
};

const HostViewSection1 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>();

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
            display: flex;
            align-items: center;
            justify-content: center;
          `}
        >
          <h1
            css={css`
              z-index: 11;
              font-size: 5vh;
              color: ${theme.colors.gray800};
            `}
          >
            직접 내 공간을 생성하고 관리하고싶다면?
          </h1>
        </div>
      )}
    </section>
  );
};

const HostViewSection2 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>();
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState(0);

  useEffect(() => {
    if (scrollPosition >= 470) {
      setEventNumber(1);
      return;
    }
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
              top: -10%;
              left: 38.5%;
              height: 75%;
              z-index: 11;
              font-size: 5vh;
              color: ${theme.colors.gray800};
              animation: ${animation.fadeIn} 1.5s;
            `}
          >
            공책과 함께 하세요.
          </h1>

          {eventNumber >= 1 && (
            <>
              <img
                css={css`
                  position: absolute;
                  top: 10%;
                  left: 12%;
                  height: 36%;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                `}
                src={createSpace}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 60%;
                  left: 12%;
                  height: 36%;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                `}
                src={createSpace}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 10%;
                  right: 12%;
                  height: 36%;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                `}
                src={createSpace}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 60%;
                  right: 12%;
                  height: 36%;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                `}
                src={createSpace}
                alt=""
              />
            </>
          )}
        </div>
      )}
    </section>
  );
};

const HostViewSection3 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>();

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
            display: flex;
            align-items: center;
            justify-content: center;
          `}
        >
          <h1
            css={css`
              z-index: 11;
              font-size: 5vh;
              color: ${theme.colors.gray800};
            `}
          >
            지금 바로 시작하기
          </h1>
        </div>
      )}
    </section>
  );
};

export default Home;
