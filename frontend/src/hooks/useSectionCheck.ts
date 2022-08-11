import { useMemo } from 'react';

import { SectionType } from '@/types';

const useSectionCheck = (sections: SectionType[]) => {
  if (sections.length === 0) return { totalCount: 0, checkedCount: false, percent: 0, isAllChecked: false };

  const sectionsAllCheckMap = new Map();
  const sectionCheckLists = sections.map(section => {
    const newSectionCheckList = section.tasks.map(task => task.checked);

    sectionsAllCheckMap.set(
      `${section.id}`,
      newSectionCheckList.every(isCheck => isCheck)
    );

    return newSectionCheckList;
  });

  const jobCheckList = sectionCheckLists?.reduce((prev, cur) => prev.concat(...cur));

  const totalCount = useMemo(() => jobCheckList?.length || 0, [jobCheckList]);
  const checkedCount = useMemo(() => jobCheckList?.filter(check => check === true).length || 0, [jobCheckList]);

  const percent = useMemo(() => Math.ceil((checkedCount / totalCount) * 100), [checkedCount, totalCount]);

  const isAllChecked = totalCount === checkedCount;

  return {
    sectionsAllCheckMap,
    totalCount,
    checkedCount,
    percent,
    isAllChecked,
  };
};

export default useSectionCheck;
