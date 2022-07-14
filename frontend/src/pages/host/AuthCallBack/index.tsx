import { css } from '@emotion/react';

import useGitHubLogin from '@/hooks/useGithubLogin';

const AuthCallBack: React.FC = () => {
  useGitHubLogin();

  return (
    <div
      css={css`
        display: flex;
        height: 100vh;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      `}
    >
      <div>로그인 진행 중...</div>
    </div>
  );
};

export default AuthCallBack;
