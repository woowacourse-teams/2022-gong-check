import { useQuery } from 'react-query';

import SpaceCard from '@/components/user/SpaceCard';

import apis from '@/apis';

import logo from '@/assets/logoTitle.png';

import styles from './styles';

const SpaceList: React.FC = () => {
  const { data: spaceData } = useQuery(['spaces'], apis.getSpaces);

  return (
    <div css={styles.layout}>
      <img css={styles.logo} src={logo} alt="공간 체크" />
      <span css={styles.text}>사용하실 공간을 선택해주세요.</span>
      {spaceData?.spaces.length === 0 ? (
        <div css={styles.empty}>관리자가 생성한 공간 없어요</div>
      ) : (
        spaceData?.spaces.map(space => (
          <SpaceCard spaceName={space.name} imageUrl={space.imageUrl} key={space.id} id={space.id} />
        ))
      )}
      {}
    </div>
  );
};

export default SpaceList;
