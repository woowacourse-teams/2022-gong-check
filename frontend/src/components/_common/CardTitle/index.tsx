import { css } from '@emotion/react';

import theme from '@/styles/theme';

interface CardTitleProps {
  children: React.ReactNode;
}

const CardTitle = ({ children }: CardTitleProps) => {
  return (
    <span
      css={css`
        color: ${theme.colors.white};
        background-color: ${theme.colors.shadow60};
        font-size: 56px;
        padding: 8px 16px 4px;
        border-radius: 16px;
        text-align: center;
      `}
    >
      {children}
    </span>
  );
};

export default CardTitle;
