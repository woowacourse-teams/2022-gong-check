import { HiPlus } from 'react-icons/hi';

import styles from './styles';

interface ImagePaintedLabelProps extends React.LabelHTMLAttributes<HTMLLabelElement> {
  imageUrl: string;
}

interface ImageChangeBoxProps {
  imageUrl: string;
  onChangeImage?: (e: React.FormEvent<HTMLInputElement>) => void;
}

interface ImageBoxMainProps {
  children?: React.ReactNode;
}

const ImagePaintedLabel: React.FC<ImagePaintedLabelProps> = ({ imageUrl }) => {
  return <label css={styles.imagePaintedLabel(imageUrl)} />;
};

const ImageChangeBox: React.FC<ImageChangeBoxProps> = ({ imageUrl, onChangeImage }) => {
  return (
    <label css={styles.imageChangeBox(imageUrl, imageUrl ? 'none' : 'dashed')} htmlFor="file">
      <input
        css={styles.imageInput}
        name="imageInput"
        type="file"
        id="file"
        accept="image/gif, image/jpg, image/jpeg, image/png, image/svg"
        onChange={onChangeImage}
      />
      {!imageUrl && (
        <div css={styles.iconBox}>
          <HiPlus size={50} />
        </div>
      )}
      <p css={styles.imageCoverText}>{imageUrl ? '이미지 수정 시 클릭해 주세요.' : '이미지를 추가해 주세요.'}</p>
    </label>
  );
};

const ImageBoxMain: React.FC<ImageBoxMainProps> = ({ children }) => {
  return <>{children}</>;
};

export const ImageBox = Object.assign(ImageBoxMain, {
  paintedLabel: ImagePaintedLabel,
  changeBox: ImageChangeBox,
});
