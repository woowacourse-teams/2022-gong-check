import useSpaceCreate from './useSpaceCreate';

import Button from '@/components/common/Button';
import ImageBox from '@/components/host/ImageBox';
import useImageBox from '@/components/host/ImageBox/useImageBox';
import SpaceInfo from '@/components/host/SpaceInfo';

import useSpaceForm from '@/hooks/useSpaceForm';

import styles from './styles';

const SpaceCreate: React.FC = () => {
  const { isCreateSpace, onCreateSpace } = useSpaceCreate();
  const { onSubmitCreateSpace } = useSpaceForm();
  const { imageUrl, onChangeImg } = useImageBox();

  return (
    <div css={styles.layout}>
      {isCreateSpace ? (
        <form onSubmit={e => onSubmitCreateSpace(e, imageUrl)} encType="multipart/form-data">
          <SpaceInfo type={'create'}>
            <ImageBox type={'create'} imageUrl={imageUrl} onChangeImg={onChangeImg} />
          </SpaceInfo>
        </form>
      ) : (
        <div css={styles.contents}>
          <p css={styles.text}>등록된 공간이 없습니다.</p>
          <Button onClick={onCreateSpace}>새로운 공간 추가</Button>
        </div>
      )}
    </div>
  );
};

export default SpaceCreate;
