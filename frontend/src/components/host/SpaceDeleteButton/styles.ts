import { css } from '@emotion/react';

import theme from '@/styles/theme';

const spaceDeleteButton = css`
  width: auto;
  height: 2rem;
  padding: 0 12px;
  font-size: 0.9rem;
  margin: 0 0 16px 12px;
  background-color: ${theme.colors.red};
  color: ${theme.colors.white};

  svg {
    height: 24px;
    width: 24px;
    transform: translateY(3px);
  }

  @media screen and (min-width: 1024px) {
    svg {
      height: 16px;
      width: 16px;
      margin-right: 4px;
    }
  }

  @media screen and (max-width: 1023px) {
    span {
      display: none;
    }
  }
`;

const styles = { spaceDeleteButton };

export default styles;
