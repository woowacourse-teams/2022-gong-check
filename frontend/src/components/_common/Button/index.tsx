/**  @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
}

const Button = ({ children, ...props }: ButtonProps) => {
  return (
    <button
      css={css`
        background: #68c1f8;
        box-sizing: border-box;
        width: 224px;
        height: 48px;
        border-radius: 12px;
        color: white;
        border: none;
        cursor: pointer;
        margin: 24px;
      `}
      {...props}
    >
      {children}
    </button>
  );
};

export default Button;
