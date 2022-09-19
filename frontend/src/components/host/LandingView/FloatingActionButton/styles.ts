import { css } from '@emotion/react';

import theme from '@/styles/theme';

const floatingActionButton = (eventNumber: number) => css`
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
`;

const styles = { floatingActionButton };

export default styles;
