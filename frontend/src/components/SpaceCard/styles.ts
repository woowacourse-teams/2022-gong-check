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
  text-shadow: 0 0 4px black;
  background-image: linear-gradient(${theme.colors.shadow40}, ${theme.colors.shadow40}), url(${imageUrl});
  background-size: cover;
  cursor: pointer;

  :hover {
    transform: scale(1.01);
  }
`;

const title = css`
  color: ${theme.colors.white};
  font-size: 32px;
  background-image: linear-gradient(transparent 90%, ${theme.colors.primary} 10%);
`;

const styles = { spaceCard, title };

export default styles;
