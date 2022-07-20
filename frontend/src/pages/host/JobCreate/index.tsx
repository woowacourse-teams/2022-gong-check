import Button from '@/components/common/Button';
import SectionCard from '@/components/host/SectionCard';

import useSections from '@/hooks/useSections';

import styles from './styles';

const DATA = {
  name: '청소',
  sections: [
    {
      name: '대강의실',
      tasks: [{ name: '책상 닦기' }, { name: '칠판 닦기' }],
    },
    {
      name: '소강의실',
      tasks: [{ name: '책상 닦기' }, { name: '칠판 닦기' }],
    },
  ],
};

const JobCreate: React.FC = () => {
  const { sections, createSection, editSection, deleteSection, createTask, editTask, deleteTask } = useSections();

  const onClick = () => {
    alert('생성되었습니다.');
  };

  return (
    <div css={styles.layout}>
      <div css={styles.contents}>
        <div css={styles.header}>
          <h1>{DATA.name}</h1>
          <Button onClick={onClick}>생성 완료</Button>
        </div>
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
export default JobCreate;
