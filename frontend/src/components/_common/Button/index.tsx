/**  @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import theme from '@/styles/theme';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
}

const Button = ({ children, ...props }: ButtonProps) => {
  return (
    <button
      css={css`
        background: ${theme.colors.primary};
        width: 224px;
        height: 48px;
        border-radius: 12px;
        font-size: 16px;
        font-weight: 600;
        color: ${theme.colors.white};
        margin: 24px;
      `}
      {...props}
    >
      {children}
    </button>
  );
};

export default Button;
