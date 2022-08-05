import styles from './styles';

type CheckBoxProps = {
  id: string;
  checked: boolean;
  onChange?: (e: React.MouseEvent<HTMLElement> | React.ChangeEvent<HTMLElement>) => void;
};

const CheckBox: React.FC<CheckBoxProps> = ({ id, checked = false, onChange }) => {
  return (
    <>
      <input css={styles.input} type="checkbox" id={id} checked={checked} onChange={onChange} readOnly />
      <label css={styles.label} htmlFor={id} onClick={onChange} />
    </>
  );
};

export default CheckBox;
