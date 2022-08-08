import { AxiosResponse } from 'axios';

import { ID, SectionType } from '@/types';
import { ApiJobActiveData, ApiJobData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const getJobs = async (spaceId: ID) => {
  const { data }: AxiosResponse<ApiJobData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces/${spaceId}/jobs`,
  });

  return data;
};

const getJobActive = async (jobId: ID) => {
  const { data }: AxiosResponse<ApiJobActiveData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/active`,
  });

  return data;
};

const postNewJob = (spaceId: ID, name: string, sections: SectionType[]) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/spaces/${spaceId}/jobs`,
    data: {
      name,
      sections,
    },
  });
};

const putJob = (jobId: ID, name: string, sections: SectionType[]) => {
  return axiosInstanceToken({
    method: 'PUT',
    url: `/api/jobs/${jobId}`,
    data: {
      name,
      sections,
    },
  });
};

const deleteJob = (jobId: ID) => {
  return axiosInstanceToken({
    method: 'DELETE',
    url: `/api/jobs/${jobId}`,
  });
};

const apiJobs = { getJobs, getJobActive, postNewJob, putJob, deleteJob };

export default apiJobs;
