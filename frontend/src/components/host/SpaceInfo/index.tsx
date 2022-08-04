import useSpaceInfo from './useSpaceInfo';

import Button from '@/components/common/Button';
import ImageBox from '@/components/host/ImageBox';

import styles from './styles';

interface SpaceInfoProps {
  type: 'read' | 'create' | 'update';
  inputText?: '' | string;
  data?: { name: string; imageUrl: string; id: number } | undefined;
}

const SpaceInfo: React.FC<SpaceInfoProps> = ({ type = 'read', inputText = '', data }) => {
  const { name, imageUrl, isActiveSubmit, onChangeSpaceName, onClickEditSpaceInfo } = useSpaceInfo(data, type);

  return (
    <div css={styles.spaceInfo}>
      <div css={styles.titleWrapper}>
        <p css={styles.title}>공간 정보</p>
        {type === 'read' ? (
          <Button type="button" css={styles.button({ isActive: true })} onClick={onClickEditSpaceInfo}>
            수정하기
          </Button>
        ) : (
          <Button type="submit" css={styles.button({ isActive: isActiveSubmit })}>
            {type === 'create' ? '생성하기' : '수정완료'}
          </Button>
        )}
      </div>
      <div css={styles.imageContainer}>
        <p css={styles.subTitle}>대표이미지</p>
        <div css={styles.imageWrapper}>
          <ImageBox type={type} imageUrl={imageUrl} />
        </div>
      </div>
      <div css={styles.inputContainer}>
        <div css={styles.inputWrapper}>
          <p css={styles.subTitle}>공간 이름</p>
          {type === 'read' ? (
            <input css={styles.input} name="nameInput" type="text" value={name} readOnly />
          ) : (
            <input
              css={styles.input}
              name="nameInput"
              placeholder="이름을 입력하세요."
              type="text"
              defaultValue={name || inputText || ''}
              maxLength={10}
              onChange={onChangeSpaceName}
              required
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default SpaceInfo;
