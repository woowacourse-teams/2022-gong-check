import { css } from '@emotion/react';

import theme from '@/styles/theme';

const layout = css`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const cover = (imageUrl: string) => css`
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
  text-shadow: 0 0 4px black;
  background-image: linear-gradient(transparent 90%, ${theme.colors.primary} 10%);
  width: fit-content;
`;

const text = css`
  font-weight: 600;
  color: #84817a;
  margin-bottom: 24px;
`;

const arrow = css`
  color: white;
  cursor: pointer;
`;

const styles = { layout, cover, coverText, text, arrow };

export default styles;
