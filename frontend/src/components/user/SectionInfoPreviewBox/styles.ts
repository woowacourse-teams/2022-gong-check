import { css } from '@emotion/react';

import theme from '@/styles/theme';

const wrapper = css`
  position: relative;
`;

const imageWrapper = css`
  width: 60px;
  height: 48px;
  overflow: hidden;
  position: relative;
  border-radius: 8px;
  box-shadow: 2px 2px 4px 0px ${theme.colors.gray500};
`;

const image = css`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const icon = css`
  position: absolute;
  bottom: -8px;
  right: -2px;
  color: ${theme.colors.green};
`;

const styles = { wrapper, imageWrapper, image, icon };

export default styles;
