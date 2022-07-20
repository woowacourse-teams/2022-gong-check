import useJobUpdate from './useJobUpdate';

import JobControl from '@/components/host/JobControl';
import SectionCard from '@/components/host/SectionCard';

import useSections from '@/hooks/useSections';

import styles from './styles';

const JobUpdate: React.FC = () => {
  const { sections, createSection, editSection, deleteSection, createTask, editTask, deleteTask } = useSections();

  const { newJobName, onChangeJobName, onClickCreateNewJob } = useJobUpdate(sections);

  return (
    <div css={styles.layout}>
      <div css={styles.contents}>
        <JobControl jobName={newJobName} onChangeJobName={onChangeJobName} onClickCreateNewJob={onClickCreateNewJob} />
        <div css={styles.grid}>
          {sections.map((section, sectionIndex) => (
            <SectionCard
              section={section}
              sectionIndex={sectionIndex}
              createTask={createTask}
              editSection={editSection}
              editTask={editTask}
              deleteTask={deleteTask}
              deleteSection={deleteSection}
              key={section.id}
            />
          ))}
          <div css={styles.createCard} onClick={createSection}>
            +
          </div>
        </div>
      </div>
    </div>
  );
};
export default JobUpdate;
