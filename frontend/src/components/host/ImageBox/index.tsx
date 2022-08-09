import { HiPlus } from 'react-icons/hi';

import styles from './styles';

interface ImageBoxProps {
  type: 'read' | 'create' | 'update';
  imageUrl: string;
  onChangeImage?: (e: React.FormEvent<HTMLInputElement>) => void;
}

interface ImageLabelBoxProps extends React.LabelHTMLAttributes<HTMLLabelElement> {
  children?: React.ReactNode;
  imageUrl: string;
}

const ImageLabelBox: React.FC<ImageLabelBoxProps> = ({ children, imageUrl, ...props }) => {
  return (
    <label css={styles.imageBox(imageUrl, imageUrl ? 'none' : 'dashed')} {...props}>
      {children}
    </label>
  );
};

const ImageBox: React.FC<ImageBoxProps> = ({ type, imageUrl, onChangeImage }) => {
  if (type === 'read') {
    return <ImageLabelBox imageUrl={imageUrl} />;
  }

  return (
    <ImageLabelBox htmlFor="file" imageUrl={imageUrl}>
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
    </ImageLabelBox>
  );
};

export default ImageBox;
