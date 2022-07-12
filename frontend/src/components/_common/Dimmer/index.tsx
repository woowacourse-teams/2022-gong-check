import { css } from '@emotion/react';

import useModal from '@/hooks/useModal';

import theme from '@/styles/theme';

interface DimmerProps {
  children: React.ReactNode;
  isAbleClick?: boolean;
}

const Dimmer = ({ children, isAbleClick = true }: DimmerProps) => {
  const { closeModal } = useModal();

  const handleClickDimed = (e: React.MouseEvent<HTMLElement>) => {
    if (e.currentTarget !== e.target) return;
    if (isAbleClick) closeModal();
  };

  return (
    <div
      css={css`
        background-color: ${theme.colors.shadow80};
        width: 400px;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
      `}
      onClick={handleClickDimed}
    >
      {children}
    </div>
  );
};

export default Dimmer;
