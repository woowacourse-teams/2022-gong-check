import { css } from '@emotion/react';

import theme from '@/styles/theme';

const container = css`
  max-width: 400px;
  min-width: 320px;
  max-height: 400px;
  width: 80%;
  background-color: ${theme.colors.white};
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 32px;
  border-radius: 18px;
  gap: 16px;
`;

const title = css`
  margin: 0;
`;

const imageWrapper = css`
  width: 90%;
  height: auto;
  overflow: hidden;
  border-radius: 8px;
`;

const image = css`
  width: 100%;
  height: 240px;
  max-height: 240px;
  object-fit: cover;
`;

const description = css`
  margin: 16px 0;
`;

const styles = { container, title, description, imageWrapper, image };

export default styles;
