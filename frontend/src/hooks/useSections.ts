import { nanoid } from 'nanoid';
import { useState } from 'react';

import { SectionType } from '@/types';

const useSections = () => {
  const [sections, setSections] = useState<SectionType[]>([]);

  const updateSection = (sections: SectionType[]) => {
    setSections(sections);
  };

  const createSection = () => {
    setSections(prev => {
      return [...prev, { id: nanoid(), name: `새 구역 ${prev.length + 1}`, tasks: [] }];
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
      prev.map((section, targetIndex) => {
        if (targetIndex === sectionIndex) {
          return {
            id: prev[sectionIndex].id,
            name: prev[sectionIndex].name,
            tasks: [
              ...prev[sectionIndex].tasks,
              { id: nanoid(), name: `새 작업 ${prev[sectionIndex].tasks.length + 1}` },
            ],
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

  return { sections, updateSection, createSection, editSection, deleteSection, createTask, editTask, deleteTask };
};

export default useSections;
