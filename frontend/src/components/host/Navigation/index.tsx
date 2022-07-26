import { CgHomeAlt, CgGirl } from 'react-icons/cg';
import { useNavigate, useParams } from 'react-router-dom';

import navigationLogo from '@/assets/navigationLogo.png';

import styles from './styles';

type SpaceType = {
  name: string;
  imageUrl: string;
  id: number;
};

interface NavigationProps {
  spaces: SpaceType[] | undefined;
}

const Navigation: React.FC<NavigationProps> = ({ spaces }) => {
  const navigate = useNavigate();
  const { spaceId } = useParams();

  const onClickSpace = (spaceId: number) => {
    navigate(`${spaceId}`);
  };

  const onClickNewSpace = () => {
    navigate('/host/manage/spaceCreate');
  };

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
          {spaces?.map(space => {
            const isSelectedSpace = space.id === Number(spaceId);

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
