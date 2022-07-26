import { GoMarkGithub } from 'react-icons/go';

import styles from './styles';

const GITHUB_LOGIN_URL_PROD = 'https://github.com/login/oauth/authorize?client_id=e78f7565dee502d18ca2';
const GITHUB_LOGIN_URL_DEV = 'https://github.com/login/oauth/authorize?client_id=964df4781c35de18694a';

let GITHUB_LOGIN_URL;

const GitHubLoginButton = () => {
  process.env.NODE_ENV === 'development'
    ? (GITHUB_LOGIN_URL = GITHUB_LOGIN_URL_DEV)
    : (GITHUB_LOGIN_URL = GITHUB_LOGIN_URL_PROD);

  return (
    <a href={GITHUB_LOGIN_URL} css={styles.wrapper}>
      <GoMarkGithub size={32} />
      <p css={styles.text}>GitHub 로그인</p>
    </a>
  );
};

export default GitHubLoginButton;
