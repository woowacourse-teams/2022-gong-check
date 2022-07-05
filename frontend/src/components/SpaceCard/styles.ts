import { css } from '@emotion/react';

const spaceCard = ({ imageURL }: { imageURL: string }) => css`
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.4);
  border-radius: 24px;
  width: 240px;
  height: 240px;
  margin: 24px;
  background: url(${imageURL});
  background-size: cover;

  &:hover {
    transform: scale(1.01);
    cursor: pointer;
  }
`;

const styles = { spaceCard };

export default styles;
