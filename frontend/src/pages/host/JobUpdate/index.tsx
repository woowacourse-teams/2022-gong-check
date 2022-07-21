import useJobUpdate from './useJobUpdate';

import JobControl from '@/components/host/JobControl';
import SectionCard from '@/components/host/SectionCard';

import useSections from '@/hooks/useSections';

import styles from './styles';

const JobUpdate: React.FC = () => {
  const { sections, updateSection, createSection, editSection, deleteSection, createTask, editTask, deleteTask } =
    useSections();

  const { jobName, onChangeJobName, onClickUpdateJob } = useJobUpdate(sections, updateSection);

  return (
    <div css={styles.layout}>
      <div css={styles.contents}>
        <JobControl
          mode="update"
          jobName={jobName || ''}
          onChangeJobName={onChangeJobName}
          onClickControlJob={onClickUpdateJob}
        />
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
