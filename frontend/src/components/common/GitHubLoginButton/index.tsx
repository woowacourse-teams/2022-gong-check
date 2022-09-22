import { GoMarkGithub } from '@react-icons/all-files/go/GoMarkGithub';

import styles from './styles';

const GitHubLoginButton = () => {
  return (
    <a href={process.env.REACT_APP_GITHUB_LOGIN_URL} css={styles.wrapper}>
      <GoMarkGithub size={32} />
      <p css={styles.text}>GitHub 로그인</p>
    </a>
  );
};

export default GitHubLoginButton;
