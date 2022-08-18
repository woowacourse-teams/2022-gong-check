import MainSection from './MainSection';
import { css } from '@emotion/react';
import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useLazyLoading from '@/hooks/useLazyLoading';
import useScroll from '@/hooks/useScroll';

import createSpace from '@/assets/createSpace.png';
import dashboard from '@/assets/dashboard.png';
import edit2 from '@/assets/edit2.png';
import edit from '@/assets/edit.png';
import mobileView1 from '@/assets/mobileView1.png';
import mobileView2 from '@/assets/mobileView2.png';
import mobileView3 from '@/assets/mobileView3.png';
import mobileView4 from '@/assets/mobileView4.png';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const FloatingActionButton: React.FC = () => {
  const navigate = useNavigate();
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState(0);

  const onClick = () => {
    navigate('/host');
  };

  useEffect(() => {
    if (scrollPosition >= 550) {
      setEventNumber(1);
      return;
    }
    setEventNumber(0);
  }, [scrollPosition]);

  return (
    <div>
      <div
        css={css`
          ${eventNumber === 1 &&
          `
            transform: translateX(calc(-50vw + 105px))  scale(1.4);
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
  return (
    <div
      css={css`
        width: 100vw;
        background-color: ${theme.colors.background};
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
    if (scrollPosition >= 60 && scrollPosition < 100) {
      setEventNumber(1);
      return;
    }
    if (scrollPosition >= 100) {
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
                  right: 10%;
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
    if (scrollPosition >= 160 && scrollPosition < 200) {
      setEventNumber(1);
      return;
    }
    if (scrollPosition >= 200) {
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
                  left: 6%;
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
    if (scrollPosition >= 270) {
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
                  height: 68%;
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
                  height: 68%;
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
                  left: 40.4%;
                  height: 75%;
                  z-index: 11;
                  font-size: 2.4vh;
                  color: ${theme.colors.gray800};
                  animation: ${animation.fadeIn} 1.5s;
                  animation-fill-mode: forwards;

                  b {
                    color: ${theme.colors.green};
                  }
                `}
              >
                여러명이 <b>동시에</b> 같은 업무 체크 가능
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
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState(0);

  console.log(scrollPosition);
  useEffect(() => {
    if (scrollPosition >= 410) {
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
        position: relative;
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
                top: 80%;
                left: 33%;
                height: 75%;
                z-index: 11;
                font-size: 5vh;
                color: ${theme.colors.gray800};
                animation: ${animation.fadeIn} 1.5s;

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
      )}
    </section>
  );
};

const HostViewSection2 = () => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>();
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState(0);

  useEffect(() => {
    if (scrollPosition >= 480) {
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
          {eventNumber >= 1 && (
            <>
              <h1
                css={css`
                  position: absolute;
                  top: 8%;
                  left: 39%;
                  height: 75%;
                  z-index: 11;
                  font-size: 4vh;
                  color: ${theme.colors.gray800};
                  animation: ${animation.fadeIn} 1.5s;
                  animation-fill-mode: forwards;
                `}
              >
                공간 관리자 페이지 제공
              </h1>
              <img
                css={css`
                  position: absolute;
                  top: 22%;
                  left: 16%;
                  height: 30%;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                  border-radius: 4px;
                  border: 4px solid ${theme.colors.primary};
                `}
                src={createSpace}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 60%;
                  left: 16%;
                  height: 30%;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                  border-radius: 4px;
                  border: 4px solid ${theme.colors.primary};
                `}
                src={dashboard}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 22%;
                  right: 16%;
                  height: 30%;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                  border-radius: 4px;
                  border: 4px solid ${theme.colors.green};
                `}
                src={edit}
                alt=""
              />
              <img
                css={css`
                  position: absolute;
                  top: 60%;
                  right: 16%;
                  height: 30%;
                  animation: ${animation.moveUp} 1.5s;
                  animation-fill-mode: forwards;
                  z-index: 11;
                  border-radius: 4px;
                  border: 4px solid ${theme.colors.green};
                `}
                src={edit2}
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
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>(0.3);

  return (
    <section
      css={css`
        width: 100vw;
        height: 38vh;
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
              animation: ${animation.moveDown} 1.5s;
              animation-fill-mode: forwards;
            `}
          >
            지금 바로
          </h1>
        </div>
      )}
    </section>
  );
};

export default MainPage;
