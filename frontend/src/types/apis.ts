import { JobType, SectionType, SpaceType } from '@/types';

export type ApiSpacesData = {
  spaces: SpaceType[];
  hasNext: boolean;
};

export type ApiJobData = {
  jobs: JobType[];
  hasNext: boolean;
};

export type ApiTaskData = {
  sections: SectionType[];
};

export type ApiJobActiveData = {
  active: boolean;
};

export type ApiTokenData = {
  token: string;
};
