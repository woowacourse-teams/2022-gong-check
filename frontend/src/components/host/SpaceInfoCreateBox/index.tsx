import useSpaceCreateForm from './useSpaceCreateForm';

import Button from '@/components/common/Button';
import { ImageBox } from '@/components/host/ImageBox';
import { SpaceInfo } from '@/components/host/SpaceInfo';

import useImage from '@/hooks/useImage';

import styles from './styles';

const SpaceInfoCreateBox: React.FC = () => {
  const { isActiveSubmit, onSubmitCreateSpace, onChangeSpaceName } = useSpaceCreateForm();
  const { imageUrl, onChangeImage } = useImage();

  return (
    <form onSubmit={e => onSubmitCreateSpace(e, imageUrl)} encType="multipart/form-data">
      <SpaceInfo>
        <SpaceInfo.header>
          <Button type="submit" css={styles.button({ isActive: isActiveSubmit })}>
            생성하기
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
            maxLength={10}
            onChange={onChangeSpaceName}
            required
          />
        </SpaceInfo.InputBox>
      </SpaceInfo>
    </form>
  );
};

export default SpaceInfoCreateBox;
