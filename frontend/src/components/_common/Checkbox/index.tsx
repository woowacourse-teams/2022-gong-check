/**  @jsxImportSource @emotion/react */
import styles from './styles';

styles;
type CheckBoxProps = {
  id: string;
  checked: boolean;
  onChange?: (e: React.MouseEvent<HTMLElement> | React.ChangeEvent<HTMLElement>) => void;
};

const CheckBox = ({ id, checked = false, onChange }: CheckBoxProps) => {
  return (
    <>
      <input css={styles.input} type="checkbox" id={id} checked={checked} onChange={onChange} />
      <label css={styles.label} htmlFor={id} onClick={onChange} />
    </>
  );
};

export default CheckBox;
