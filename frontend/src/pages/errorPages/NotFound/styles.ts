import { css } from '@emotion/react';

import animation from '@/styles/animation';
import theme from '@/styles/theme';

const layout = css`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const text404 = css`
  font-size: 5rem;
  margin: 0;
  padding: 0;
`;

const description = css`
  color: ${theme.colors.primary};
  font-size: 1.4rem;
`;

const content = css`
  position: relative;
  top: 0;
  z-index: 1;
`;

const homeCoverWrapper = css`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const homeCover = css`
  margin: 1em 0;
  animation: ${animation.wave} 2s alternate linear infinite;

  source,
  img {
    width: 300px;
    aspect-ratio: auto 1 / 1;
    @media screen and (min-width: 481px) {
      width: 400px;
    }
  }
`;

const styles = { layout, text404, description, content, homeCoverWrapper, homeCover };

export default styles;
