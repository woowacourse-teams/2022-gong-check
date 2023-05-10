import useJobUpdate from './useJobUpdate';

import JobControl from '@/components/host/JobControl';
import SectionCard from '@/components/host/SectionCard';

import styles from './styles';

const JobUpdate: React.FC = () => {
  const { sections, createSection, jobName, onChangeJobName, onClickUpdateJob } = useJobUpdate();

  return (
    <div css={styles.layout}>
      <form css={styles.contents} onSubmit={onClickUpdateJob}>
        <JobControl mode="update" jobName={jobName || ''} onChangeJobName={onChangeJobName} />
        <div css={styles.grid}>
          {sections.map((section, sectionIndex) => (
            <SectionCard section={section} sectionIndex={sectionIndex} key={section.id} />
          ))}
          <div css={styles.createCard} onClick={createSection}>
            +
          </div>
        </div>
      </form>
    </div>
  );
};
export default JobUpdate;
