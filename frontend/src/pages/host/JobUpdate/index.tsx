import { nanoid } from 'nanoid';
import React, { useState } from 'react';

import SectionCard from '@/components/host/SectionCard';

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

const JobUpdate: React.FC = () => {
  const [sections, setSections] = useState<{ name: string; tasks: { name: string }[] }[]>([]);

  const createSection = () => {
    setSections(prev => {
      return [...prev, { name: `새 구역 ${prev.length + 1}`, tasks: [] }];
    });
  };

  const editSection = (sectionIndex: number, value: string) => {
    setSections(prev => {
      const previousSection = [...prev];
      previousSection[sectionIndex].name = value;
      return previousSection;
    });
  };

  const deleteSection = (sectionIndex: number) => {
    if (confirm('해당 구역을 삭제하시겠습니까?')) {
      setSections(prev => {
        const previousSection = [...prev];
        return previousSection.filter((section, targetIndex) => targetIndex !== sectionIndex);
      });
    }
  };

  const createTask = (sectionIndex: number) => {
    setSections(prev =>
      prev.map((section, index) => {
        if (index === sectionIndex) {
          return {
            name: prev[sectionIndex].name,
            tasks: [...prev[sectionIndex].tasks, { name: `새 작업 ${prev[sectionIndex].tasks.length + 1}` }],
          };
        }
        return section;
      })
    );
  };

  const editTask = (sectionIndex: number, taskIndex: number, value: string) => {
    setSections(prev => {
      const previousSection = [...prev];
      previousSection[sectionIndex].tasks[taskIndex].name = value;
      return previousSection;
    });
  };

  const deleteTask = (sectionIndex: number, taskIndex: number) => {
    if (confirm('해당 작업을 삭제하시겠습니까?')) {
      setSections(prev => {
        const previousSection = [...prev];
        previousSection[sectionIndex].tasks = previousSection[sectionIndex].tasks.filter((task, targetIndex) => {
          return targetIndex !== taskIndex;
        });
        return previousSection;
      });
    }
  };

  return (
    <div css={styles.layout}>
      <div css={styles.contents}>
        <p css={styles.pageTitle}>{DATA.name}</p>
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
              key={nanoid()}
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
