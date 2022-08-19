import { css } from '@emotion/react';

import GitHubLoginButton from '@/components/common/GitHubLoginButton';

import homeCover from '@/assets/homeCover.png';
import logoTitle from '@/assets/logoTitle.png';

import theme from '@/styles/theme';

const Home: React.FC = () => {
  const isMobile = innerWidth < 600;

  return (
    <>
      <div
        id="헤더"
        css={css`
          display: flex;
          align-items: center;
          width: 100vw;
          height: 84px;
          padding: 0 48px;
          font-size: 24px;
          font-weight: 600;
          background-color: ${theme.colors.skyblue200};
          box-shadow: 0px 2px 1px 1px ${theme.colors.shadow20};

          img {
            margin-right: 10px;
          }

          span > b {
            font-size: 16px;
          }
        `}
      >
        <img src={logoTitle} alt="" width={240} />
        {!isMobile && (
          <span>
            <b>for</b> 공간 관리자
          </span>
        )}
      </div>
      <div
        id="레이아웃"
        css={css`
          display: flex;
          height: calc(100vh - 84px);
          flex-direction: column;
          align-items: center;

          img {
            margin-top: 32px;
          }
        `}
      >
        {isMobile && (
          <div
            css={css`
              margin: 20px;
              color: ${theme.colors.red};
            `}
          >
            모바일에 최적화되어있지 않습니다. 데스크탑을 이용해주세요.
          </div>
        )}
        <img src={homeCover} alt="" width={360} />
        <span
          css={css`
            margin: 32px 0 62px 0;
            color: ${theme.colors.gray800};
            font-size: ${isMobile ? '16px' : '24px'};
          `}
        >
          관리자 페이지 이용을 위해 로그인이 필요합니다.
        </span>
        <GitHubLoginButton />
      </div>
    </>
  );
};

export default Home;
