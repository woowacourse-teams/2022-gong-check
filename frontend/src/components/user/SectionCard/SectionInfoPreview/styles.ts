import { css } from '@emotion/react';

import theme from '@/styles/theme';

const wrapper = css`
  position: relative;
  cursor: pointer;
`;

const imageWrapper = (isImage: boolean) => css`
  width: 56px;
  height: 40px;
  overflow: hidden;
  border-radius: 8px;
  box-shadow: 2px 2px 4px 0px ${theme.colors.gray500};
  background-color: ${!isImage && theme.colors.skyblue200};
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
