import Button from '../../_common/Button';
import { css } from '@emotion/react';
import { HiPlus } from 'react-icons/hi';

import styles from './styles';

type SpaceInfoProps = {
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
  inputText: string;
};

const SpaceInfo: React.FC<SpaceInfoProps> = ({ onSubmit, inputText }) => {
  return (
    <div css={styles.spaceInfo}>
      <form onSubmit={onSubmit}>
        <div css={styles.titleWrapper}>
          <p css={styles.title}>공간 정보</p>
          <Button
            type="submit"
            css={css`
              width: 80px;
              height: 40px;
              margin: 0;
            `}
          >
            수정 하기
          </Button>
        </div>
        <div css={styles.imageContainer}>
          <p css={styles.subTitle}>대표이미지</p>
          <div css={styles.imageWrapper}>
            <div css={[styles.imageBox, styles.notImageBox]}>
              <div css={styles.iconBox}>
                <HiPlus size={50} />
              </div>
              <p css={styles.notImageText}>이미지를 추가해 주세요.</p>
            </div>
          </div>
        </div>
        <div css={styles.inputContainer}>
          <div css={styles.inputWrapper}>
            <p css={styles.subTitle}>공간 이름</p>
            <input css={styles.input} placeholder="이름을 입력하세요" type="text" />
          </div>
        </div>
      </form>
    </div>
  );
};

export default SpaceInfo;
