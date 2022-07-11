type SpaceType = {
  name: string;
  imageUrl: string;
  id: number;
};

export type ApiSpacesData = {
  spaces: SpaceType[];
  hasNext: boolean;
};

type JobType = {
  id: number;
  name: string;
};

export type ApiJobData = {
  jobs: JobType[];
  hasNext: boolean;
};

type TaskType = {
  id: number;
  name: string;
  checked: boolean;
};

type SectionType = {
  id: number;
  name: string;
  tasks: TaskType[];
};

export type ApiTaskData = {
  sections: SectionType[];
};

export type ApiJobActiveData = {
  active: boolean;
};
