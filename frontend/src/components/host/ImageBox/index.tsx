import { HiPlus } from 'react-icons/hi';

import styles from './styles';

interface ImageBoxProps {
  type: 'read' | 'create' | 'update';
  data?: { name: string; imageUrl: string; id: number };
  imageUrl: string | undefined;
  onChangeImg?: (e: React.FormEvent<HTMLInputElement>) => void;
}

interface ImageLabelBoxProps extends React.LabelHTMLAttributes<HTMLLabelElement> {
  children?: React.ReactNode;
  imageUrl: string | undefined;
}

const ImageLabelBox: React.FC<ImageLabelBoxProps> = ({ children, imageUrl, ...props }) => {
  return (
    <label css={styles.imageBox(imageUrl, imageUrl ? 'none' : 'dashed')} {...props}>
      {children}
    </label>
  );
};

const ImageBox: React.FC<ImageBoxProps> = ({ type, imageUrl, onChangeImg }) => {
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
        onChange={onChangeImg}
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
