import { css } from '@emotion/react';

import theme from '@/styles/theme';

const jobCard = css`
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-radius: 24px;
  max-width: 400px;
  height: 180px;
  width: 80%;
  margin: 16px;
  padding: 0 16px;
  background-color: ${theme.colors.gray100};
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow40};
  cursor: pointer;

  :hover {
    transform: scale(1.02);
    background-color: ${theme.colors.gray200};
  }
`;

const textWrapper = css`
  display: flex;
  flex-direction: column;
  font-size: 28px;
  font-weight: 600;
  color: ${theme.colors.black};

  span + span {
    margin: 8px 0;
    font-size: 16px;
    font-size: 200;
  }
`;

const styles = { jobCard, textWrapper };

export default styles;
