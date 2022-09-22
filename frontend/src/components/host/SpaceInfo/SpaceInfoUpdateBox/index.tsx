import useSpaceUpdateForm from './useSpaceUpdateForm';

import Button from '@/components/common/Button';
import LoadingOverlay from '@/components/common/LoadingOverlay';
import { ImageBox } from '@/components/host/ImageBox';
import { SpaceInfo } from '@/components/host/SpaceInfo';

import useImage from '@/hooks/useImage';

import { ID, SpaceType } from '@/types';

import styles from './styles';

interface SpaceInfoUpdateBox {
  spaceId: ID;
  spaceData: SpaceType;
}

const SpaceInfoUpdateBox: React.FC<SpaceInfoUpdateBox> = ({ spaceData, spaceId }) => {
  const { isActiveSubmit, onChangeSpaceName, onSubmitUpdateSpace } = useSpaceUpdateForm();
  const { imageUrl, onChangeImage, isImageLoading } = useImage(spaceData?.imageUrl);

  return (
    <>
      <form css={styles.form} onSubmit={e => onSubmitUpdateSpace(e, imageUrl, spaceId)} encType="multipart/form-data">
        <SpaceInfo>
          <SpaceInfo.header>
            <Button type="submit" css={styles.button({ isActive: isActiveSubmit })}>
              수정완료
            </Button>
          </SpaceInfo.header>
          <SpaceInfo.ImageBox>
            <ImageBox>
              <ImageBox.changeBox imageUrl={imageUrl} onChangeImage={onChangeImage} />
            </ImageBox>
          </SpaceInfo.ImageBox>
          <SpaceInfo.InputBox>
            <input
              css={styles.input}
              name="nameInput"
              placeholder="이름을 입력하세요."
              type="text"
              defaultValue={spaceData.name}
              maxLength={10}
              onChange={onChangeSpaceName}
              required
            />
          </SpaceInfo.InputBox>
        </SpaceInfo>
      </form>
      {isImageLoading && <LoadingOverlay />}
    </>
  );
};

export default SpaceInfoUpdateBox;
