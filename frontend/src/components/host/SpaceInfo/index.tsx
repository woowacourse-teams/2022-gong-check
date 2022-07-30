import useSpaceInfo from './useSpaceInfo';
import { HiPlus } from 'react-icons/hi';

import Button from '@/components/common/Button';

import styles from './styles';

interface SpaceInfoProps {
  type: 'read' | 'create' | 'update';
  inputText?: '' | string;
  data?: { name: string; imageUrl: string; id: number } | undefined;
}

const SpaceInfo: React.FC<SpaceInfoProps> = ({ type = 'read', inputText = '', data }) => {
  const { imageUrl, labelRef, isActiveSubmit, isExistImageUrl, onChangeImg, onChangeSpaceName, onClickEditSpaceInfo } =
    useSpaceInfo(data, type);

  return (
    <div css={styles.spaceInfo}>
      <div css={styles.titleWrapper}>
        <p css={styles.title}>공간 정보</p>
        {type === 'read' ? (
          <Button type="button" css={styles.button({ isActive: true })} onClick={onClickEditSpaceInfo}>
            수정하기
          </Button>
        ) : type === 'create' ? (
          <Button type="submit" css={styles.button({ isActive: isActiveSubmit })}>
            생성하기
          </Button>
        ) : (
          <Button type="submit" css={styles.button({ isActive: isActiveSubmit })}>
            수정완료
          </Button>
        )}
      </div>
      <div css={styles.imageContainer}>
        <p css={styles.subTitle}>대표이미지</p>
        <div css={styles.imageWrapper}>
          {type === 'read' ? (
            <label css={styles.imageBox(data?.imageUrl)} htmlFor="file" ref={labelRef}></label>
          ) : (
            <label css={styles.imageBox(imageUrl, isExistImageUrl ? 'none' : 'dashed')} htmlFor="file" ref={labelRef}>
              <input
                css={styles.imageInput}
                name="imageInput"
                type="file"
                id="file"
                accept="image/*"
                onChange={onChangeImg}
              />
              {!isExistImageUrl && (
                <div css={styles.iconBox}>
                  <HiPlus size={50} />
                </div>
              )}
              <p css={styles.imageCoverText}>
                {isExistImageUrl ? '이미지 수정 시 클릭해 주세요.' : '이미지를 추가해 주세요.'}
              </p>
            </label>
          )}
        </div>
      </div>
      <div css={styles.inputContainer}>
        <div css={styles.inputWrapper}>
          <p css={styles.subTitle}>공간 이름</p>
          {type === 'read' ? (
            <input css={styles.input} name="nameInput" type="text" defaultValue={data?.name} readOnly />
          ) : (
            <input
              css={styles.input}
              name="nameInput"
              placeholder="이름을 입력하세요"
              type="text"
              defaultValue={data?.name || inputText || ''}
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
