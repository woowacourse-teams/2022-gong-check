import { AxiosError } from 'axios';

import { JobType, SectionType, SpaceType } from '@/types';

export type ApiError = AxiosError<{ message: string }>;

export type ApiSpaceData = {
  name: string;
  imageUrl: string;
  id: number;
};

export type ApiSpacesData = {
  spaces: ApiSpaceData[];
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

type SubmissionType = {
  submissionId: number;
  jobId: number;
  jobName: string;
  author: string;
  createdAt: string;
};

export type ApiSubmissionData = {
  submissions: SubmissionType[] | undefined;
  hasNext: boolean;
};

export type ApiHostTokenData = {
  token: string;
  alreadyJoin: boolean;
};

export type ApiHostIdData = {
  id: number;
};
