export type ID = number | string;

export type SpaceType = {
  name: string;
  imageUrl: string;
  id: ID;
};

export type JobType = {
  id: ID;
  name: string;
};

export type SectionType = {
  id: ID;
  name: string;
  description: string;
  imageUrl: string;
  tasks: TaskType[];
};

export type TaskType = {
  id: ID;
  name: string;
  checked?: boolean;
  description: string;
  imageUrl: string;
};

export type ScreenModeType = 'DESKTOP' | 'MOBILE';

export type UserImageType = {
  '160w': string;
  '240w': string;
  '320w': string;
  '480w': string;
  fallback: string;
};

export type HostImageType = {
  '280w': string;
  '360w': string;
  '540w': string;
  '800w': string;
  fallback: string;
};
