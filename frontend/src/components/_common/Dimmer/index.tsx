/**  @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import theme from '../../../styles/theme';

interface DimmerProps {
  children: React.ReactNode;
  requiredSubmit?: boolean;
  closeModal: () => void;
}

const Dimmer = ({ children, requiredSubmit = false, closeModal }: DimmerProps) => {
  const handleClickDimed = (e: React.MouseEvent<HTMLElement>) => {
    if (e.currentTarget !== e.target) return;
    if (requiredSubmit) closeModal();
  };

  return (
    <div
      css={css`
        background-color: ${theme.colors.shadow80};
        width: 400px;
        height: 100vh;
        position: relative;
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
