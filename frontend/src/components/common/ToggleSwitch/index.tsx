import styles from './styles';

interface ToggleSwitchProps {
  toggle: boolean;
  onClickSwitch: () => void;
  first: string;
  second: string;
}

const ToggleSwitch: React.FC<ToggleSwitchProps> = ({ toggle, onClickSwitch, first, second }) => {
  return (
    <button css={styles.wrapper} onClick={onClickSwitch}>
      <div css={styles.element(toggle)}>{first}</div>
      <div css={styles.element(!toggle)}>{second}</div>
      <div css={styles.ball(toggle)} />
    </button>
  );
};

export default ToggleSwitch;
