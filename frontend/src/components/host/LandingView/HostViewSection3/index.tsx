import useHostViewSection3 from './useHostViewSection3';

import styles from './styles';

const HostViewSection3: React.FC = () => {
  const { eventNumber, sectionRef } = useHostViewSection3();

  return (
    <section css={styles.layout} ref={sectionRef}>
      <div css={styles.content}>{eventNumber === 1 && <h1 css={styles.title}>지금 바로</h1>}</div>
    </section>
  );
};

export default HostViewSection3;
