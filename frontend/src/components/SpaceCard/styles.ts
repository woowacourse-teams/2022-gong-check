import { css } from '@emotion/react';

import theme from '@/styles/theme';

const spaceCard = ({ imageUrl }: { imageUrl: string }) => css`
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
  width: 90%;
  height: 25vh;
  margin: 24px;
  padding: 24px;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow40};
  border-radius: 24px;
  background-image: linear-gradient(#00000000, ${theme.colors.black}), url(${imageUrl});
  background-size: cover;
  &:hover {
    transform: scale(1.01);
    cursor: pointer;
  }
`;

const title = css`
  color: ${theme.colors.white};
  font-size: 44px;
  background-image: linear-gradient(transparent 95%, ${theme.colors.primary} 5%);
`;

const styles = { spaceCard, title };

export default styles;
