import { css } from '@emotion/react';

import GitHubLoginButton from '@/components/_common/GitHubLoginButton';

const Login: React.FC = () => {
  return (
    <>
      <div
        id="헤더"
        css={css`
          width: 100vw;
          height: 80px;
          background-color: gray;
        `}
      >
        메인 페이지 헤더
      </div>
      <div
        id="레이아웃"
        css={css`
          display: flex;
          height: 100vh;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        `}
      >
        <GitHubLoginButton />
      </div>
    </>
  );
};

export default Login;
