type SpaceType = {
  name: string;
  imageUrl: string;
  id: number;
};

type JobType = {
  id: number;
  name: string;
};

type SectionType = {
  id: number;
  name: string;
  tasks: TaskType[];
};

type TaskType = {
  id: number;
  name: string;
  checked: boolean;
};

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
