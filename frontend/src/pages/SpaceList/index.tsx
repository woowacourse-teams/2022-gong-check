import { useQuery } from 'react-query';

import PageTitle from '@/components/_common/PageTitle';

import SpaceCard from '@/components/SpaceCard';

import apis from '@/apis';

import styles from './styles';

const SpaceList = () => {
  const { data } = useQuery(['spaces'], apis.getSpaces, { suspense: true });

  return (
    <div css={styles.layout}>
      <PageTitle>
        장소 목록 (<span>{data?.spaces.length}</span>)
      </PageTitle>
      <div css={styles.contents}>
        {data?.spaces.map(space => (
          <SpaceCard spaceName={space.name} imageUrl={space.imageUrl} key={space.id} id={space.id} />
        ))}
      </div>
    </div>
  );
};

export default SpaceList;
