import { GoMarkGithub } from 'react-icons/go';

import styles from './styles';

const GitHubLoginButton = () => {
  return (
    <a href={process.env.REACT_APP_GITHUB_LOGIN_URL} css={styles.wrapper}>
      <GoMarkGithub size={24} />
      <span css={styles.text}>GitHub</span>
    </a>
  );
};

export default GitHubLoginButton;
