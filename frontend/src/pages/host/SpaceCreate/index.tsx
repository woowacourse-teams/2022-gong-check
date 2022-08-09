import ImageBox from '@/components/host/ImageBox';
import useImageBox from '@/components/host/ImageBox/useImageBox';
import SpaceInfo from '@/components/host/SpaceInfo';

import useSpaceForm from '@/hooks/useSpaceForm';

import styles from './styles';

const SpaceCreate: React.FC = () => {
  const { onSubmitCreateSpace } = useSpaceForm();
  const { imageUrl, onChangeImage } = useImageBox();

  return (
    <div css={styles.layout}>
      <form onSubmit={e => onSubmitCreateSpace(e, imageUrl)} encType="multipart/form-data">
        <SpaceInfo type="create">
          <ImageBox type="create" imageUrl={imageUrl} onChangeImage={onChangeImage} />
        </SpaceInfo>
      </form>
    </div>
  );
};

export default SpaceCreate;
