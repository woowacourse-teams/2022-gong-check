import { css } from '@emotion/react';
import { useRef, useState } from 'react';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

import { ScreenModeType } from '@/types';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

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

export default HostViewSection3;
