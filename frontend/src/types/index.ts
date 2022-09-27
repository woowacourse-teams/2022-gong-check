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
