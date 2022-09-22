import GitHubLoginButton from '@/components/common/GitHubLoginButton';

import homeCover from '@/assets/homeCover.png';
import homeCoverWebp from '@/assets/homeCover.webp';
import logoTitle from '@/assets/logoTitle.png';
import logoTitleWebp from '@/assets/logoTitle.webp';

import styles from './styles';

const Home: React.FC = () => {
  return (
    <>
      <div css={styles.header}>
        <img srcSet={`${logoTitleWebp}`} src={logoTitle} alt="공책" />
      </div>
      <div css={styles.layout}>
        <img srcSet={`${homeCoverWebp}`} src={homeCover} alt="함께 사용하는 우리의 공간 우리가 체크하자, 공책" />
        <GitHubLoginButton />
      </div>
    </>
  );
};

export default Home;
