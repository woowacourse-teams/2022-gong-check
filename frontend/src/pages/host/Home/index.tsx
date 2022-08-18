import { css } from '@emotion/react';
import { useEffect, useRef, useState } from 'react';

import GitHubLoginButton from '@/components/common/GitHubLoginButton';

import useLazyLoading from '@/hooks/useLazyLoading';
import useScroll from '@/hooks/useScroll';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const Home: React.FC = () => {
  return (
    <div
      css={css`
        width: 100vw;
        position: absolute;
      `}
    >
      <MainSection />
      <Section1 target={1} />
      <Section2 target={2} />
      <Section1 target={3} />
      <Section1 target={4} />
      <Section1 target={5} />
      <Section1 target={6} />
    </div>
  );
};

const MainSection = () => {
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
    >
      <div
        css={css`
          width: 50%;
          height: 50%;
          background-color: ${theme.colors.primary};
          color: white;
          font-size: 32px;
          font-weight: 600;
        `}
      >
        메인
      </div>
    </section>
  );
};

const Section1 = ({ target }: any) => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>(1);
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState<number>(0);

  useEffect(() => {
    if (scrollPosition > 120 && scrollPosition < 140) {
      setEventNumber(1);
      return;
    }
    if (scrollPosition > 140 && scrollPosition < 160) {
      setEventNumber(2);
      return;
    }
    setEventNumber(0);
  }, [scrollPosition]);

  return (
    <section
      css={css`
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100vw;
        height: 100vh;
        position: sticky;
        top: 0;
        background-color: ${theme.colors.background};
        visibility: ${isLoaded ? 'visible' : 'hidden'};
      `}
      ref={sectionRef}
    >
      {isLoaded && (
        <div
          css={css`
            width: 50%;
            height: 50%;
            background-color: ${theme.colors.green};
            color: white;
            font-size: 32px;
            font-weight: 600;
            animation: ${animation.moveUp} 1.5s;
            animation-fill-mode: forwards;
          `}
        >
          {eventNumber === 0 && target}
          {eventNumber === 1 && '1 - 첫번째 이벤트 발생'}
          {eventNumber === 2 && '1 - 두번째 이벤트 발생'}
        </div>
      )}
    </section>
  );
};

const Section2 = ({ target }: any) => {
  const { isLoaded, targetRef: sectionRef } = useLazyLoading<HTMLSelectElement>(1);
  const { scrollPosition } = useScroll();
  const [eventNumber, setEventNumber] = useState<number>(0);

  useEffect(() => {
    if (scrollPosition > 220 && scrollPosition < 240) {
      setEventNumber(1);
      return;
    }
    if (scrollPosition > 240 && scrollPosition < 260) {
      setEventNumber(2);
      return;
    }
    setEventNumber(0);
  }, [scrollPosition]);

  return (
    <section
      css={css`
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100vw;
        height: 100vh;
        position: sticky;
        top: 0;
        background-color: ${theme.colors.background};
        visibility: ${isLoaded ? 'visible' : 'hidden'};
      `}
      ref={sectionRef}
    >
      {isLoaded && (
        <div
          css={css`
            width: 50%;
            height: 50%;
            background-color: ${theme.colors.red};
            color: white;
            font-size: 32px;
            font-weight: 600;
            animation: ${animation.moveUp} 1.5s;
            animation-fill-mode: forwards;
          `}
        >
          {eventNumber === 0 && target}
          {eventNumber === 1 && '2 - 첫번째 이벤트 발생'}
          {eventNumber === 2 && '2 - 두번째 이벤트 발생'}
        </div>
      )}
    </section>
  );
};

export default Home;
