import { css } from '@emotion/react';

const spaceCard = ({ imageURL }: { imageURL: string }) => css`
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  box-shadow: 3px 3px 3px 0px rgba(0, 0, 0, 0.4);
  border-radius: 24px;
  width: 240px;
  height: 240px;
  margin: 16px;
  background: url(${imageURL});
  background-size: cover;

  &:hover {
    transform: scale(1.01);
    cursor: pointer;
  }
`;

const layout = css`
  min-height: 100vh;
  padding: 8px 32px;
`;

const pageTitle = css`
  font-size: 20px;
  font-weight: 600;
`;

const contents = css`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
`;

const cardTitle = css`
  color: white;
  background-color: rgba(0, 0, 0, 0.6);
  font-size: 56px;
  padding: 8px 16px 4px;
  border-radius: 16px;
  text-align: center;
`;

const styles = { spaceCard, layout, pageTitle, contents, cardTitle };

export default styles;
