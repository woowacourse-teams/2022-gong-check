/**  @jsxImportSource @emotion/react */
import { useEffect, useState } from 'react';

import PageTitle from '@/components/_common/PageTitle';

import SpaceCard from '@/components/SpaceCard';

import apis from '@/apis';

import styles from './styles';

type SpaceType = {
  name: string;
  imageURL: string;
  id: number;
};

const SpaceList = () => {
  const [spaces, setSpaces] = useState<Array<SpaceType>>([]);

  useEffect(() => {
    const getSpaces = async () => {
      const {
        data: { spaces },
      } = await apis.getSpaces();

      setSpaces(spaces);
    };

    getSpaces();
  }, []);

  return (
    <div css={styles.layout}>
      <PageTitle>
        장소 목록 (<span>2</span>)
      </PageTitle>
      <div css={styles.contents}>
        {spaces.map(space => (
          <SpaceCard spaceName={space.name} imageURL={space.imageURL} key={space.id} id={space.id} />
        ))}
      </div>
    </div>
  );
};

export default SpaceList;
