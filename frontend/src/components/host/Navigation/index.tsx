import { useState } from 'react';
import { CgHomeAlt, CgGirl } from 'react-icons/cg';
import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import apiSpace from '@/apis/space';

import { ID } from '@/types';

import navigationLogo from '@/assets/navigationLogo.png';

import styles from './styles';

const Navigation: React.FC = () => {
  const navigate = useNavigate();

  const { spaceId } = useParams();

  const [selectedSpaceId, setSelectedSpaceId] = useState<ID | undefined>(spaceId);

  const { data: spaceData } = useQuery(['spaces'], apiSpace.getSpaces, {
    suspense: true,
  });

  const onClickSpace = (spaceId: number) => {
    setSelectedSpaceId(spaceId);
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
