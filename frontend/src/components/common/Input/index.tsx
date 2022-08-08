import styles from './styles';

type InputProps = React.ClassAttributes<HTMLInputElement> & React.InputHTMLAttributes<HTMLInputElement>;

const Input: React.FC<InputProps> = ({ placeholder, onChange, ...props }) => {
  return <input css={styles.input} placeholder={placeholder} onChange={onChange} {...props} />;
};

export default Input;
