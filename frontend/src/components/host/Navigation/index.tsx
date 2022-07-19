import { CgHomeAlt, CgGirl } from 'react-icons/cg';

import navigationLogo from '@/assets/navigationLogo.png';

import styles from './styles';

const SPACE_DATA = [
  { id: 1, name: '잠실 캠퍼스' },
  { id: 2, name: '선릉 캠퍼스' },
];

const Navigation: React.FC = () => {
  return (
    <div css={styles.layout}>
      <div id="로고 들어가는 위치" css={styles.logo}>
        <img src={navigationLogo} alt="" css={styles.logoImage} />
      </div>

      <div id="메뉴" css={styles.category}>
        <span css={styles.categoryTitle}>Menu</span>
        <div css={styles.categoryList}>
          <div css={styles.categoryTextWrapper}>
            <CgGirl size={20} />
            <span> 내 정보 수정</span>
          </div>
        </div>
      </div>

      <div id="나의 공간 목록" css={styles.category}>
        <span css={styles.categoryTitle}>나의 공간 목록</span>
        <div css={styles.categoryList}>
          {SPACE_DATA.map(space => (
            <div css={styles.categoryTextWrapper} key={space.id}>
              <CgHomeAlt size={20} />
              <span>{space.name}</span>
            </div>
          ))}
          <div css={styles.addNewSpace}>
            <span>+ 새로운 공간 추가</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Navigation;
