import { css } from '@emotion/react';

import theme from '@/styles/theme';

const profile = css`
  display: flex;
  padding: 0 8px;
  justify-content: flex-start;
  align-items: center;
  font-size: 0.9em;
  font-weight: 600;
  color: ${theme.colors.gray800};

  img {
    border-radius: 50%;
    margin-right: 4px;
  }
`;

const styles = {
  profile,
};

export default styles;
