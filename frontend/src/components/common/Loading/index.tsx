import styles from './styles';

const Loading: React.FC = () => {
  return (
    <div css={styles.layout}>
      <div css={styles.spinner}>
        <div css={styles.faceSpinner}>
          <div css={styles.faceSpinnerEye}></div>
        </div>
        <b css={styles.text}>loading...</b>
      </div>
    </div>
  );
};
export default Loading;
