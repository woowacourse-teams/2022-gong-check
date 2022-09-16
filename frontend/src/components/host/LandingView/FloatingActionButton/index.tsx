import { css } from '@emotion/react';
import { RefObject, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

import theme from '@/styles/theme';

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

export default FloatingActionButton;
