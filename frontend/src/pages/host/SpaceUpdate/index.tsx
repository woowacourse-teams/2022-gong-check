import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import SpaceInfoUpdateBox from '@/components/host/SpaceInfo/SpaceInfoUpdateBox';

import apiSpace from '@/apis/space';

import { ID } from '@/types';

import styles from './styles';

const SpaceUpdate: React.FC = () => {
  const { spaceId } = useParams() as { spaceId: ID };

  const { data: spaceData } = useQuery(['space', spaceId], () => apiSpace.getSpace(spaceId));

  return (
    <div css={styles.layout}>
      <SpaceInfoUpdateBox spaceId={spaceId} spaceData={spaceData!} />
    </div>
  );
};

export default SpaceUpdate;
