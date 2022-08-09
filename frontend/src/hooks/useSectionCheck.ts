import { useMemo } from 'react';

import { SectionType } from '@/types';

const useSectionCheck = (sections: SectionType[]) => {
  const sectionCheckLists = sections.map(section => section.tasks.map(task => task.checked));
  const jobCheckList = sectionCheckLists?.reduce((prev, cur) => prev.concat(...cur));

  const totalCount = useMemo(() => jobCheckList?.length || 0, [jobCheckList]);
  const checkedCount = useMemo(() => jobCheckList?.filter(check => check === true).length || 0, [jobCheckList]);

  const percent = useMemo(() => Math.ceil((checkedCount / totalCount) * 100), [checkedCount, totalCount]);

  const isAllChecked = totalCount === checkedCount;

  return {
    totalCount,
    checkedCount,
    percent,
    isAllChecked,
  };
};

export default useSectionCheck;
