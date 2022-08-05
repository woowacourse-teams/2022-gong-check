import { nanoid } from 'nanoid';
import { useRecoilState, useResetRecoilState } from 'recoil';

import { sectionsState } from '@/recoil/sections';

import { SectionType } from '@/types';

const useSections = () => {
  const [sections, setSections] = useRecoilState(sectionsState);
  const resetSections = useResetRecoilState(sectionsState);

  const getSectionInfo = (sectionIndex: number) => {
    return sections[sectionIndex];
  };

  const updateSection = (sections: SectionType[]) => {
    setSections(sections);
  };

  const createSection = () => {
    setSections(prev => {
      return [...prev, { id: nanoid(), name: '', description: '', imageUrl: '', tasks: [] }];
    });
  };

  const editSection = (sectionIndex: number, newName: string) => {
    setSections(prev => {
      const previousSection = JSON.parse(JSON.stringify(prev)) as SectionType[];
      previousSection[sectionIndex].name = newName;
      return previousSection;
    });
  };

  const editSectionDetail = (sectionIndex: number, newImageUrl: string, newDescription: string) => {
    setSections(prev => {
      const previousSection = JSON.parse(JSON.stringify(prev)) as SectionType[];
      previousSection[sectionIndex].imageUrl = newImageUrl;
      previousSection[sectionIndex].description = newDescription;
      return previousSection;
    });
  };

  const deleteSection = (sectionIndex: number) => {
    if (confirm('해당 구역을 삭제하시겠습니까?')) {
      setSections(prev => {
        const previousSection = JSON.parse(JSON.stringify(prev)) as SectionType[];
        return previousSection.filter((section, targetIndex) => targetIndex !== sectionIndex);
      });
    }
  };

  const getTaskInfo = (sectionIndex: number, taskIndex: number) => {
    return sections[sectionIndex].tasks[taskIndex];
  };

  const createTask = (sectionIndex: number) => {
    setSections(prev =>
      prev.map((section, targetIndex) => {
        if (targetIndex === sectionIndex) {
          return {
            id: prev[sectionIndex].id,
            name: prev[sectionIndex].name,
            description: prev[sectionIndex].description,
            imageUrl: prev[sectionIndex].imageUrl,
            tasks: [...prev[sectionIndex].tasks, { id: nanoid(), name: '', description: '', imageUrl: '' }],
          };
        }
        return section;
      })
    );
  };

  const editTask = (sectionIndex: number, taskIndex: number, newName: string) => {
    setSections(prev => {
      const previousSection = JSON.parse(JSON.stringify(prev)) as SectionType[];
      previousSection[sectionIndex].tasks[taskIndex].name = newName;
      return previousSection;
    });
  };

  const editTaskDetail = (sectionIndex: number, taskIndex: number, newImageUrl: string, newDescription: string) => {
    setSections(prev => {
      const previousSection = JSON.parse(JSON.stringify(prev)) as SectionType[];
      previousSection[sectionIndex].tasks[taskIndex].imageUrl = newImageUrl;
      previousSection[sectionIndex].tasks[taskIndex].description = newDescription;
      return previousSection;
    });
  };

  const deleteTask = (sectionIndex: number, taskIndex: number) => {
    if (confirm('해당 작업을 삭제하시겠습니까?')) {
      setSections(prev => {
        const previousSection = JSON.parse(JSON.stringify(prev)) as SectionType[];
        previousSection[sectionIndex].tasks = previousSection[sectionIndex].tasks.filter((task, targetIndex) => {
          return targetIndex !== taskIndex;
        });
        return previousSection;
      });
    }
  };

  return {
    sections,
    getSectionInfo,
    resetSections,
    updateSection,
    createSection,
    editSection,
    editSectionDetail,
    deleteSection,
    getTaskInfo,
    createTask,
    editTask,
    editTaskDetail,
    deleteTask,
  };
};

export default useSections;
