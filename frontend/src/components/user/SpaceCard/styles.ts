import { css } from '@emotion/react';

import theme from '@/styles/theme';

const spaceCard = (imageUrl: string) => css`
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
  max-width: 400px;
  height: 180px;
  width: 80%;
  margin: 16px;
  padding: 24px;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow40};
  border-radius: 24px;
  background-image: linear-gradient(${theme.colors.shadow50}, ${theme.colors.shadow50}), url(${imageUrl});
  background-size: cover;
  cursor: pointer;

  :hover {
    transform: scale(1.02);
  }
`;

const title = css`
  color: ${theme.colors.white};
  font-size: 28px;
  text-shadow: -1px -1px 6px #000, 1px -1px 6px #000, -1px 1px 6px #000, 1px 1px 6px #000;
  background-image: linear-gradient(transparent 90%, ${theme.colors.primary} 10%);
`;

const arrow = css`
  border-radius: 100%;
  box-shadow: 0 0 4px 2px ${theme.colors.shadow90};
  margin-left: auto;
  align-self: center;
`;

const styles = { spaceCard, title, arrow };

export default styles;
