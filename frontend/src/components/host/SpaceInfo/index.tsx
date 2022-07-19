import { css } from '@emotion/react';
import React, { useEffect, useMemo, useRef, useState } from 'react';
import { HiPlus } from 'react-icons/hi';
import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';

import theme from '@/styles/theme';

import styles from './styles';

interface SpaceInfoProps {
  onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
  inputText?: '' | string;
  isEditMode?: boolean;
  data?: { name: string; imageUrl: string; id: number };
}

const SpaceInfo: React.FC<SpaceInfoProps> = ({ onSubmit, inputText = '', isEditMode = true, data }) => {
  const navigate = useNavigate();
  const labelRef = useRef(null);
  const [imageUrl, setImageUrl] = useState('');
  const [isActiveSubmit, setIsActiveSubmit] = useState(false);

  const isExistimageUrl = useMemo(() => !!imageUrl, [imageUrl]);

  const handleChangeImg = (e: React.FormEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    if (!input.files?.length || !labelRef.current) {
      return;
    }

    const file = input.files[0];
    const src = URL.createObjectURL(file);

    setImageUrl(src);
  };

  const handleChangeSpaceName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    const isExistValue = input.value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  const onClickEditSpaceInfo = () => {
    console.log('onClickEditSpaceInfo');
    // navigate()
    // TODO 수정하기 페이지 이동
  };

  if (!isEditMode) {
    return (
      <div css={styles.spaceInfo}>
        <div css={styles.titleWrapper}>
          <span css={styles.title}>공간 정보</span>
          <Button
            type="button"
            css={css`
              ${styles.button}
            `}
            onClick={onClickEditSpaceInfo}
          >
            수정하기
          </Button>
        </div>
        <div css={styles.imageContainer}>
          <p css={styles.subTitle}>대표이미지</p>
          <div css={styles.imageWrapper}>
            <label
              css={css`
                ${styles.imageBox}

                background-image: url(${data?.imageUrl});
              `}
              htmlFor="file"
              ref={labelRef}
            >
              {/* <input
                css={styles.imageInput}
                name="imageInput"
                type="file"
                id="file"
                accept="image/*"
                onChange={handleChangeImg}
              /> */}
            </label>
          </div>
        </div>
        <div css={styles.inputContainer}>
          <div css={styles.inputWrapper}>
            <p css={styles.subTitle}>공간 이름</p>
            <input
              css={styles.input}
              name="nameInput"
              placeholder="이름을 입력하세요"
              type="text"
              defaultValue={data?.name}
            />
          </div>
        </div>
      </div>
    );
  }

  return (
    <div css={styles.spaceInfo}>
      <form onSubmit={onSubmit}>
        <div css={styles.titleWrapper}>
          <p css={styles.title}>공간 정보</p>
          <Button
            type="submit"
            css={css`
              ${styles.button}
              background: ${isActiveSubmit ? theme.colors.primary : theme.colors.gray400};
            `}
          >
            생성하기
          </Button>
        </div>
        <div css={styles.imageContainer}>
          <p css={styles.subTitle}>대표이미지</p>
          <div css={styles.imageWrapper}>
            <label
              css={css`
                ${styles.imageBox}
                border-style: ${isExistimageUrl ? 'none' : 'dashed'};
                background-image: url(${imageUrl});
              `}
              htmlFor="file"
              ref={labelRef}
            >
              <input
                css={styles.imageInput}
                name="imageInput"
                type="file"
                id="file"
                accept="image/*"
                onChange={handleChangeImg}
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
          </div>
        </div>
        <div css={styles.inputContainer}>
          <div css={styles.inputWrapper}>
            <p css={styles.subTitle}>공간 이름</p>
            <input
              css={styles.input}
              name="nameInput"
              placeholder="이름을 입력하세요"
              type="text"
              defaultValue={inputText || ''}
              onChange={handleChangeSpaceName}
              required
            />
          </div>
        </div>
      </form>
    </div>
  );
};

export default SpaceInfo;
