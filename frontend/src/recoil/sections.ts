import { atom, selectorFamily } from 'recoil';

import { SectionType } from '@/types';

export const sectionsState = atom<SectionType[]>({
  key: 'sections/sectionState',
  default: [],
});

export const sectionState = selectorFamily<SectionType, number>({
  key: 'sections/section',
  get:
    sectionIndex =>
    ({ get }) => {
      const sections = get(sectionsState) as SectionType[];
      return sections[sectionIndex];
    },
});
