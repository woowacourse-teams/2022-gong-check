import { css } from '@emotion/react';

const jobCard = css`
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  border-radius: 16px;
  width: 320px;
  height: 144px;
  margin: 24px;
  box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.3);
  &:hover {
    transform: scale(1.01);
    cursor: pointer;
  }
`;

const styles = { jobCard };

export default styles;
