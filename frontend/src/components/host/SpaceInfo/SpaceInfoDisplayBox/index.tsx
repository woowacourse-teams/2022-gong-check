import useSpaceInfoDisplayBox from './useSpaceInfoDisplayBox';

import Button from '@/components/common/Button';
import { ImageBox } from '@/components/host/ImageBox';
import { SpaceInfo } from '@/components/host/SpaceInfo';

import { SpaceType } from '@/types';

import styles from './styles';

interface SpaceInfoDisplayBoxProps {
  spaceData: SpaceType;
}

const SpaceInfoDisplayBox: React.FC<SpaceInfoDisplayBoxProps> = ({ spaceData }) => {
  const { onClickEditSpaceInfo } = useSpaceInfoDisplayBox(spaceData);

  return (
    <SpaceInfo>
      <SpaceInfo.header>
        <Button type="button" css={styles.button} onClick={onClickEditSpaceInfo}>
          수정하기
        </Button>
      </SpaceInfo.header>
      <SpaceInfo.ImageBox>
        <ImageBox>
          <ImageBox.paintedLabel imageUrl={spaceData?.imageUrl || ''} />
        </ImageBox>
      </SpaceInfo.ImageBox>
      <SpaceInfo.InputBox>
        <SpaceInfo.nameText>{spaceData.name}</SpaceInfo.nameText>
      </SpaceInfo.InputBox>
    </SpaceInfo>
  );
};

export default SpaceInfoDisplayBox;
