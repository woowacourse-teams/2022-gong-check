import useSpaceInfo from './useSpaceInfo';
import { HiPlus } from 'react-icons/hi';

import Button from '@/components/common/Button';

import styles from './styles';

interface SpaceInfoProps {
  onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
  inputText?: '' | string;
  isEditMode?: boolean;
  data?: { name: string; imageUrl: string; id: number } | undefined;
}

const SpaceInfo: React.FC<SpaceInfoProps> = ({ onSubmit, inputText = '', isEditMode = true, data }) => {
  const { imageUrl, labelRef, isActiveSubmit, isExistimageUrl, onChangeImg, onChangeSpaceName, onClickEditSpaceInfo } =
    useSpaceInfo(data);

  if (isEditMode) {
    return (
      <form onSubmit={onSubmit} encType="multipart/form-data">
        <SpaceInfoTemplate
          buttonComponent={
            <Button type="submit" css={styles.button({ isActive: isActiveSubmit })}>
              생성하기
            </Button>
          }
          imageComponent={
            <label css={styles.imageBox(imageUrl, isExistimageUrl ? 'none' : 'dashed')} htmlFor="file" ref={labelRef}>
              <input
                css={styles.imageInput}
                name="imageInput"
                type="file"
                id="file"
                accept="image/*"
                onChange={onChangeImg}
              />
              {!isExistimageUrl && (
                <div css={styles.iconBox}>
                  <HiPlus size={50} />
                </div>
              )}
              <p css={styles.imageCoverText}>
                {isExistimageUrl ? '이미지 수정 시 클릭해 주세요.' : '이미지를 추가해 주세요.'}
              </p>
            </label>
          }
          nameComponent={
            <input
              css={styles.input}
              name="nameInput"
              placeholder="이름을 입력하세요"
              type="text"
              defaultValue={inputText || ''}
              onChange={onChangeSpaceName}
              required
            />
          }
        />
      </form>
    );
  }

  return (
    <SpaceInfoTemplate
      buttonComponent={
        <Button type="button" css={styles.button({ isActive: true })} onClick={onClickEditSpaceInfo}>
          수정하기
        </Button>
      }
      imageComponent={<label css={styles.imageBox(data?.imageUrl)} htmlFor="file" ref={labelRef}></label>}
      nameComponent={
        <input
          css={styles.input}
          name="nameInput"
          placeholder="이름을 입력하세요"
          type="text"
          defaultValue={data?.name}
          readOnly
        />
      }
    />
  );
};

interface SpaceInfoTemplateProps {
  buttonComponent: React.ReactNode;
  imageComponent: React.ReactNode;
  nameComponent: React.ReactNode;
}

const SpaceInfoTemplate: React.FC<SpaceInfoTemplateProps> = ({ buttonComponent, imageComponent, nameComponent }) => {
  return (
    <div css={styles.spaceInfo}>
      <div css={styles.titleWrapper}>
        <p css={styles.title}>공간 정보</p>
        {buttonComponent}
      </div>
      <div css={styles.imageContainer}>
        <p css={styles.subTitle}>대표이미지</p>
        <div css={styles.imageWrapper}>{imageComponent}</div>
      </div>
      <div css={styles.inputContainer}>
        <div css={styles.inputWrapper}>
          <p css={styles.subTitle}>공간 이름</p>
          {nameComponent}
        </div>
      </div>
    </div>
  );
};

export default SpaceInfo;
