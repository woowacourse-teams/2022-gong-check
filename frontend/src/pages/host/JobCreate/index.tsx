import useJobCreate from './useJobCreate';

import JobControl from '@/components/host/JobControl';
import SectionCard from '@/components/host/SectionCard';

import styles from './styles';

const JobCreate: React.FC = () => {
  const { sections, createSection, newJobName, onChangeJobName, onClickCreateNewJob } = useJobCreate();

  return (
    <div css={styles.layout}>
      <form css={styles.contents} onSubmit={onClickCreateNewJob}>
        <JobControl mode="create" jobName={newJobName} onChangeJobName={onChangeJobName} />
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
export default JobCreate;
