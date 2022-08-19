import React from 'react';

import styles from './styles';

interface SpaceInfoHeaderProps {
  children: React.ReactNode;
}

interface SpaceInfoImageProps {
  children: React.ReactNode;
}

interface SpaceInfoInputProps {
  children: React.ReactNode;
}

interface SpaceInfoMainProps {
  children: React.ReactNode;
}

interface SpaceNameTextProps {
  children: React.ReactNode;
}

const SpaceInfoHeader: React.FC<SpaceInfoHeaderProps> = ({ children }) => {
  return (
    <div css={styles.titleWrapper}>
      <p css={styles.title}>공간 정보</p>
      {children}
    </div>
  );
};

const SpaceInfoImage: React.FC<SpaceInfoImageProps> = ({ children }) => {
  return (
    <div css={styles.imageContainer}>
      <p css={styles.subTitle}>대표이미지</p>
      <div css={styles.imageWrapper}>{children}</div>
    </div>
  );
};

const SpaceInfoInput: React.FC<SpaceInfoInputProps> = ({ children }) => {
  return (
    <div css={styles.inputContainer}>
      <div css={styles.inputWrapper}>
        <p css={styles.subTitle}>공간 이름</p>
        {children}
      </div>
    </div>
  );
};

const SpaceNameText: React.FC<SpaceNameTextProps> = ({ children }) => {
  return <p css={styles.nameText}>{children}</p>;
};

const SpaceInfoMain: React.FC<SpaceInfoMainProps> = ({ children }) => {
  return <div css={styles.spaceInfo}>{children}</div>;
};

export const SpaceInfo = Object.assign(SpaceInfoMain, {
  header: SpaceInfoHeader,
  ImageBox: SpaceInfoImage,
  InputBox: SpaceInfoInput,
  nameText: SpaceNameText,
});
