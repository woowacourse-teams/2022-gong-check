import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const cover = (imageUrl: string | undefined) => css`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 200px;
  background-image: linear-gradient(${theme.colors.shadow50}, ${theme.colors.shadow50}), url(${imageUrl});
  padding: 16px;
  background-size: cover;
  margin-bottom: 48px;
`;

const coverText = css`
  color: ${theme.colors.white};
  font-size: 36px;
  text-shadow: 0 0 4px ${theme.colors.black};
  background-image: linear-gradient(transparent 90%, ${theme.colors.primary} 10%);
  width: fit-content;
  margin-left: 16px;
`;

const text = css`
  font-weight: 600;
  color: ${theme.colors.gray500};
  margin-bottom: 24px;
`;

const arrow = css`
  color: ${theme.colors.white};
  cursor: pointer;
`;

const empty = css`
  margin-top: 56px;
  font-size: 24px;
  font-weight: 500;
`;

const styles = { layout, cover, coverText, text, arrow, empty };

export default styles;
