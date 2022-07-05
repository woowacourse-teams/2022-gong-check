/**  @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

type CardTitleProps = {
  children: React.ReactNode;
};

const CardTitle = ({ children }: CardTitleProps) => {
  return (
    <span
      css={css`
        color: white;
        background-color: rgba(0, 0, 0, 0.6);
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
