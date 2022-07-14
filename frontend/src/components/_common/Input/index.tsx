import { css } from '@emotion/react';

import theme from '@/styles/theme';

type InputProps = React.InputHTMLAttributes<HTMLInputElement>;

const Input: React.FC<InputProps> = ({ placeholder, onChange }) => {
  return (
    <input
      css={css`
        border: none;
        background-color: ${theme.colors.lightGray};
        width: 100%;
        border-radius: 12px;
        width: 256px;
        height: 48px;
        padding: 8px 16px;
        font-size: 16px;
        &::placeholder {
          color: ${theme.colors.gray};
        }
        &:focus {
          outline: none;
        }
      `}
      placeholder={placeholder}
      onChange={onChange}
    />
  );
};

export default Input;
