import { css } from '@emotion/react';

import theme from '@/styles/theme';

const spaceCard = ({ imageUrl }: { imageUrl: string }) => css`
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow40};
  border-radius: 24px;
  width: 240px;
  height: 240px;
  margin: 24px;
  background: url(${imageUrl});
  background-size: cover;
  &:hover {
    transform: scale(1.01);
    cursor: pointer;
  }
`;

const styles = { spaceCard };

export default styles;
