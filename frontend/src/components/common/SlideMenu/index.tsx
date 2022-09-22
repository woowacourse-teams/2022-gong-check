import styles from './styles';

interface SlideMenuProps {
  children: React.ReactNode;
  isShowMenu: boolean | null;
}

const SlideMenu: React.FC<SlideMenuProps> = ({ children, isShowMenu }) => {
  return <div css={styles.slideMenu(isShowMenu)}>{children}</div>;
};

export default SlideMenu;
