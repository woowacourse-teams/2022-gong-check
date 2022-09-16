import { css } from '@emotion/react';
import { useRef, useState } from 'react';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

import { ScreenModeType } from '@/types';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

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

export default HostViewSection1;
