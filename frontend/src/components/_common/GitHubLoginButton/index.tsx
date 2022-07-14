import { GoMarkGithub } from 'react-icons/go';

import styles from './styles';

const GITHUB_LOGIN_URL = 'https://github.com/login/oauth/authorize?client_id=e78f7565dee502d18ca2';

const GitHubLoginButton = () => {
  return (
    <a href={GITHUB_LOGIN_URL} css={styles.wrapper}>
      <GoMarkGithub size={32} />
      <p css={styles.text}>GitHub 로그인</p>
    </a>
  );
};

export default GitHubLoginButton;
