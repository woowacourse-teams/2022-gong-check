import { css } from '@emotion/react';

import GitHubLoginButton from '@/components/_common/GitHubLoginButton';

import homeCover from '@/assets/homeCover.png';

import theme from '@/styles/theme';

const Home: React.FC = () => {
  return (
    <>
      <div
        id="헤더"
        css={css`
          display: flex;
          align-items: center;
          width: 100vw;
          height: 64px;
          padding: 0 48px;
          font-size: 32px;
          color: white;
          background-color: ${theme.colors.primary};
        `}
      >
        <span>GongCheck</span>
      </div>
      <div
        id="레이아웃"
        css={css`
          display: flex;
          flex-direction: column;
          align-items: center;
        `}
      >
        <div
          id="커버"
          css={css`
            margin: 1em 0;
          `}
        >
          <img src={homeCover} alt="" />
        </div>
        <GitHubLoginButton />
      </div>
    </>
  );
};

export default Home;
