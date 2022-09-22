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

type ImagePathType = string;

export type UserImageType = {
  '160w': ImagePathType;
  '240w': ImagePathType;
  '320w': ImagePathType;
  '480w': ImagePathType;
  fallback: ImagePathType;
};

export type HostImageType = {
  '280w': ImagePathType;
  '360w': ImagePathType;
  '540w': ImagePathType;
  '800w': ImagePathType;
  fallback: ImagePathType;
};
