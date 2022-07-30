import styles from './styles';

interface ToggleSwitchProps {
  toggle: boolean;
  onClickSwitch: () => void;
  left: string;
  right: string;
}

const ToggleSwitch: React.FC<ToggleSwitchProps> = ({ toggle, onClickSwitch, left, right }) => {
  return (
    <button css={styles.wrapper} onClick={onClickSwitch}>
      <div css={styles.element(toggle)}>{left}</div>
      <div css={styles.element(!toggle)}>{right}</div>
      <div css={styles.ball(toggle)} />
    </button>
  );
};

export default ToggleSwitch;
