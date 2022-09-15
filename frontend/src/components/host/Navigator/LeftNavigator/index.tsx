import Menu from '../../Menu';
import { useNavigate } from 'react-router-dom';

import logo from '@/assets/navigationLogo.png';

import styles from './styles';

const LeftNavigator: React.FC = () => {
  const navigate = useNavigate();

  const onClickLogo = () => {
    if (location.pathname.split('/').length !== 4) navigate(-1);
  };

  return (
    <div css={styles.layout}>
      <div css={styles.logo} onClick={onClickLogo}>
        <img css={styles.logoImage} src={logo} alt="" />
      </div>
      <Menu />
    </div>
  );
};

export default LeftNavigator;
