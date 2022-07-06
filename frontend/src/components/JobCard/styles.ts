import { css } from '@emotion/react';
import theme from '../../styles/theme';

const jobCard = css`
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 16px;
  width: 320px;
  height: 144px;
  margin: 24px;
  box-shadow: 2px 2px 2px 2px ${theme.colors.shadow30};
  &:hover {
    transform: scale(1.01);
    cursor: pointer;
  }
`;

const styles = { jobCard };

export default styles;
