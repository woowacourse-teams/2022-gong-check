import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import SpaceInfo from '@/components/host/SpaceInfo';

import useSpaceForm from '@/hooks/useSpaceForm';

import apiSpace from '@/apis/space';

import styles from './styles';

const SpaceUpdate: React.FC = () => {
  const { spaceId } = useParams();

  const { data } = useQuery(['space', spaceId], () => apiSpace.getSpace(spaceId), { suspense: true });

  const { onSubmitUpdateSpace } = useSpaceForm();

  return (
    <div css={styles.layout}>
      <form onSubmit={e => onSubmitUpdateSpace(e, data?.imageUrl, spaceId)} encType="multipart/form-data">
        <SpaceInfo type={'update'} data={data} />
      </form>
    </div>
  );
};

export default SpaceUpdate;
