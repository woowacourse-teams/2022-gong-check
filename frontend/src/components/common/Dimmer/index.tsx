import { css } from '@emotion/react';

import useModal from '@/hooks/useModal';

import theme from '@/styles/theme';

interface DimmerProps {
  children: React.ReactNode;
  mode: 'full' | 'mobile';
  isAbleClick?: boolean;
}

const Dimmer: React.FC<DimmerProps> = ({ children, isAbleClick = true, mode = 'full' }) => {
  const { closeModal } = useModal();

  const handleClickDimmed = (e: React.MouseEvent<HTMLElement>) => {
    if (e.currentTarget === e.target && isAbleClick) closeModal();
  };

  return (
    <div
      css={css`
        background-color: ${theme.colors.shadow80};
        width: ${mode === 'full' ? '100vw' : '400px'};
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
      `}
      onClick={handleClickDimmed}
    >
      {children}
    </div>
  );
};

export default Dimmer;
