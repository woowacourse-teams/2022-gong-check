import { useMemo } from 'react';

import { SectionType } from '@/types';

const useSectionCheck = (sections: SectionType[]) => {
  const tasks = useMemo(() => sections.map(section => section.tasks.map(task => task.checked)), [sections]);
  const checkList = useMemo(
    () =>
      tasks.reduce((prev, cur) => {
        return prev.concat(...cur);
      }, []),
    [tasks]
  );
  const totalCount = useMemo(() => checkList.length, [checkList]);
  const checkCount = useMemo(() => checkList.filter(check => check === true).length, [checkList]);
  const percent = useMemo(() => Math.ceil((checkCount / totalCount) * 100), [checkCount, totalCount]);
  const isAllChecked = totalCount === checkCount;

  return {
    totalCount,
    checkCount,
    percent,
    isAllChecked,
  };
};

export default useSectionCheck;
