import { css } from '@emotion/react';

import theme from '@/styles/theme';

const jobCard = css`
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-radius: 24px;
  width: 320px;
  height: 144px;
  margin: 16px;
  padding: 0 16px;
  background-color: ${theme.colors.gray100};
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow40};
  cursor: pointer;
  :hover {
    transform: scale(1.01);
  }
`;

const textWrapper = css`
  display: flex;
  flex-direction: column;
  font-size: 30px;
  font-weight: 600;
  span + span {
    margin: 4px 0;
    font-size: 16px;
    font-size: 200;
  }
`;

const styles = { jobCard, textWrapper };

export default styles;
