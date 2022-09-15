import GitHubLoginButton from '@/components/common/GitHubLoginButton';

import homeCover from '@/assets/homeCover.png';
import logoTitle from '@/assets/logoTitle.png';

import styles from './styles';

const Home: React.FC = () => {
  return (
    <>
      <div css={styles.header}>
        <img src={logoTitle} alt="공책" />
      </div>
      <div css={styles.layout}>
        <img src={homeCover} alt="함께 사용하는 우리의 공간 우리가 체크하자, 공책" />
        <GitHubLoginButton />
      </div>
    </>
  );
};

export default Home;
