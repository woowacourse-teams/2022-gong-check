import useImageBox from '../ImageBox/useImageBox';
import useSpaceUpdateForm from './useSpaceUpdateForm';
import { useState } from 'react';

import Button from '@/components/common/Button';
import ImageBox from '@/components/host/ImageBox';
import { SpaceInfo } from '@/components/host/SpaceInfo';

import { ID, SpaceType } from '@/types';

import styles from './styles';

interface SpaceInfoUpdateBox {
  spaceId: ID;
  spaceData: SpaceType;
}

const SpaceInfoUpdateBox: React.FC<SpaceInfoUpdateBox> = ({ spaceData, spaceId }) => {
  const { onSubmitUpdateSpace } = useSpaceUpdateForm();
  const [name, setName] = useState<string>(spaceData.name);
  const { imageUrl, onChangeImage } = useImageBox(spaceData?.imageUrl);

  const [isActiveSubmit, setIsActiveSubmit] = useState(true);

  const onChangeSpaceName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    const isExistValue = input.value.length > 0;
    setIsActiveSubmit(isExistValue);
  };
  return (
    <form onSubmit={e => onSubmitUpdateSpace(e, imageUrl, spaceId)} encType="multipart/form-data">
      <SpaceInfo>
        <SpaceInfo.header>
          <Button type="submit" css={styles.button({ isActive: isActiveSubmit })}>
            수정완료
          </Button>
        </SpaceInfo.header>
        <SpaceInfo.ImageBox>
          <ImageBox type={'update'} imageUrl={imageUrl} onChangeImage={onChangeImage} />
        </SpaceInfo.ImageBox>
        <SpaceInfo.InputBox>
          <input
            css={styles.input}
            name="nameInput"
            placeholder="이름을 입력하세요."
            type="text"
            defaultValue={name}
            maxLength={10}
            onChange={onChangeSpaceName}
            required
          />
        </SpaceInfo.InputBox>
      </SpaceInfo>
    </form>
  );
};

export default SpaceInfoUpdateBox;
