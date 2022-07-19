export type ApiSpaceData = {
  name: string;
  imageUrl: string;
  id: number;
};

export type ApiSpacesData = {
  spaces: ApiSpaceData[];
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

export type ApiTokenData = {
  token: string;
};

type SubmissionType = {
  submissionId: number;
  jobId: number;
  jobName: string;
  author: string;
  createdAt: string;
};

export type ApiSubmissionData = {
  spaces: SubmissionType[];
  hasNext: boolean;
};
