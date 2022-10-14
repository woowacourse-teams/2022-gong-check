import { useEffect } from 'react';

import styles from './styles';

interface SlideMenuProps {
  children: React.ReactNode;
  isShowMenu: boolean | null;
}

const SlideMenu: React.FC<SlideMenuProps> = ({ children, isShowMenu }) => {
  useEffect(() => {
    isShowMenu ? (document.body.style.overflow = 'hidden') : (document.body.style.overflow = 'unset');
  }, [isShowMenu]);

  useEffect(() => {
    return () => {
      document.body.style.overflow = 'unset';
    };
  }, []);

  return <div css={styles.slideMenu(isShowMenu)}>{children}</div>;
};

export default SlideMenu;
