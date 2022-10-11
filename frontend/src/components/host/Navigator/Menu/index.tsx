import useMenu from './useMenu';
import { CgHomeAlt } from '@react-icons/all-files/cg/CgHomeAlt';
import { RiAccountPinCircleLine } from '@react-icons/all-files/ri/RiAccountPinCircleLine';
import { RiLockPasswordLine } from '@react-icons/all-files/ri/RiLockPasswordLine';
import { RiLogoutCircleLine } from '@react-icons/all-files/ri/RiLogoutCircleLine';

import Profile from '@/components/common/Profile';

import styles from './styles';

const Menu = () => {
  const { selectedSpaceId, spaceData, onClickPasswordUpdate, onClickSpace, onClickNewSpace, onClickLogout } = useMenu();

  return (
    <>
      <div css={styles.category}>
        <div css={styles.categoryList}>
          <span css={styles.categoryTitle}>내 정보</span>
          <Profile />
          <div css={styles.categoryTextWrapper} onClick={onClickPasswordUpdate}>
            <RiLockPasswordLine size={20} />
            <span>공간 입장코드 변경</span>
          </div>
          <div css={styles.categoryTextWrapper}>
            <RiAccountPinCircleLine size={20} />
            <span>닉네임 변경</span>
          </div>
          <div css={styles.categoryTextWrapper} onClick={onClickLogout}>
            <RiLogoutCircleLine size={20} />
            <span>로그아웃</span>
          </div>
        </div>
      </div>

      <div css={styles.category}>
        <span css={styles.categoryTitle}>내 공간 목록</span>
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
    </>
  );
};

export default Menu;
