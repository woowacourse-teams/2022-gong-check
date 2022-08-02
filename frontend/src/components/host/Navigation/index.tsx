import useHostNavigation from './useHostNavigation';
import { CgHomeAlt } from 'react-icons/cg';
import { RiLockPasswordLine } from 'react-icons/ri';

import navigationLogo from '@/assets/navigationLogo.png';

import styles from './styles';

const Navigation: React.FC = () => {
  const { selectedSpaceId, spaceData, onClickPasswordUpdate, onClickSpace, onClickNewSpace } = useHostNavigation();

  return (
    <div css={styles.layout}>
      <div css={styles.logo}>
        <img src={navigationLogo} alt="" css={styles.logoImage} />
      </div>

      <div css={styles.category}>
        <span css={styles.categoryTitle}>Menu</span>
        <div css={styles.categoryList} onClick={onClickPasswordUpdate}>
          <div css={styles.categoryTextWrapper}>
            <RiLockPasswordLine size={20} />
            <span> 공간 입장코드 변경</span>
          </div>
        </div>
      </div>

      <div css={styles.category}>
        <span css={styles.categoryTitle}>나의 공간 목록</span>
        <div css={styles.categoryList}>
          {spaceData?.spaces.map(space => {
            const isSelectedSpace = space.id === Number(selectedSpaceId);

            return (
              <div
                css={[styles.categoryTextWrapper, isSelectedSpace && styles.selectedTextWrapper]}
                key={space.id}
                onClick={() => onClickSpace(space.id)}
              >
                <CgHomeAlt size={20} />
                <span>{space.name}</span>
              </div>
            );
          })}
          <div css={styles.addNewSpace} onClick={onClickNewSpace}>
            <span>+ 새로운 공간 추가</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Navigation;
