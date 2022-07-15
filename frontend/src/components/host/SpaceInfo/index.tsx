import { css } from '@emotion/react';
import React, { useMemo, useRef, useState } from 'react';
import { HiPlus } from 'react-icons/hi';

import Button from '@/components/_common/Button';

import theme from '@/styles/theme';

import styles from './styles';

type SpaceInfoProps = {
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
  inputText: '' | string;
};

const SpaceInfo: React.FC<SpaceInfoProps> = ({ onSubmit, inputText = '' }) => {
  const labelRef = useRef(null);
  const [imgURL, setImgURL] = useState('');
  const [isActiveSubmit, setIsActiveSubmit] = useState(false);

  const isExistImgURL = useMemo(() => !!imgURL, [imgURL]);

  const handleChangeImg = (e: React.FormEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    if (!input.files?.length || !labelRef.current) {
      return;
    }

    const file = input.files[0];
    const src = URL.createObjectURL(file);

    setImgURL(src);
  };

  const handleChangeSpaceName = (e: React.FormEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    const isExistValue = input.value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  return (
    <div css={styles.spaceInfo}>
      <form onSubmit={onSubmit}>
        <div css={styles.titleWrapper}>
          <p css={styles.title}>공간 정보</p>
          <Button
            type="submit"
            css={css`
              ${styles.button}
              background: ${isActiveSubmit ? theme.colors.primary : theme.colors.gray};
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
                border-style: ${isExistImgURL ? 'none' : 'dashed'};
                background-image: url(${imgURL});
              `}
              htmlFor="file"
              ref={labelRef}
            >
              <input css={styles.imageInput} type="file" id="file" accept="image/*" onChange={handleChangeImg} />
              {!isExistImgURL && (
                <div css={styles.iconBox}>
                  <HiPlus size={50} />
                </div>
              )}
              <p css={styles.imageCoverText}>
                {isExistImgURL ? '이미지 수정 시 클릭해 주세요.' : '이미지를 추가해 주세요.'}
              </p>
            </label>
          </div>
        </div>
        <div css={styles.inputContainer}>
          <div css={styles.inputWrapper}>
            <p css={styles.subTitle}>공간 이름</p>
            <input
              css={styles.input}
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
