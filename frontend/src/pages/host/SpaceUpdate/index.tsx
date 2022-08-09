import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import ImageBox from '@/components/host/ImageBox';
import useImageBox from '@/components/host/ImageBox/useImageBox';
import SpaceInfo from '@/components/host/SpaceInfo';

import useSpaceForm from '@/hooks/useSpaceForm';

import apiSpace from '@/apis/space';

import { ID } from '@/types';

import styles from './styles';

const SpaceUpdate: React.FC = () => {
  const { spaceId } = useParams() as { spaceId: ID };

  const { data: spaceData } = useQuery(['space', spaceId], () => apiSpace.getSpace(spaceId));

  const { onSubmitUpdateSpace } = useSpaceForm();
  const { imageUrl, onChangeImage } = useImageBox(spaceData?.imageUrl);

  return (
    <div css={styles.layout}>
      <form onSubmit={e => onSubmitUpdateSpace(e, imageUrl, spaceId)} encType="multipart/form-data">
        <SpaceInfo type={'update'} space={spaceData!}>
          <ImageBox type={'update'} imageUrl={imageUrl} onChangeImage={onChangeImage} />
        </SpaceInfo>
      </form>
    </div>
  );
};

export default SpaceUpdate;
