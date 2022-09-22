import Menu from '../Menu';
import isNull from '@/utils/isNull';
import { BiMenu } from '@react-icons/all-files/bi/BiMenu';
import { BiX } from '@react-icons/all-files/bi/BiX';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import SlideMenu from '@/components/common/SlideMenu';

import logo from '@/assets/logoTitle.png';

import styles from './styles';

const TopNavigator: React.FC = () => {
  const navigate = useNavigate();

  const [isShowMenu, setIsShowMenu] = useState<boolean | null>(null);

  const onClickMenuIcon = () => setIsShowMenu(prev => !prev);

  const onClickLogo = () => {
    if (location.pathname.split('/').length !== 4) navigate(-1);
  };

  return (
    <>
      <div css={styles.layout}>
        {isShowMenu ? <BiX size={32} onClick={onClickMenuIcon} /> : <BiMenu size={32} onClick={onClickMenuIcon} />}
        <img css={styles.logo} src={logo} alt="" onClick={onClickLogo} />
      </div>
      {!isNull(isShowMenu) && (
        <SlideMenu isShowMenu={isShowMenu}>
          <Menu />
        </SlideMenu>
      )}
    </>
  );
};

export default TopNavigator;
