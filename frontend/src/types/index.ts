export type SpaceType = {
  name: string;
  imageUrl: string;
  id: number | string;
};

export type JobType = {
  id: number | string;
  name: string;
};

export type SectionType = {
  id: number | string | undefined;
  name: string;
  tasks: TaskType[];
};

export type TaskType = {
  id: number | string;
  name: string;
  checked?: boolean;
};
