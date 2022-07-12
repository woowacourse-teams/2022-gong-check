import { AxiosResponse } from 'axios';

import { ApiSpacesData, ApiJobData, ApiTaskData, ApiJobActiveData, ApiTokenData } from '@/types/apis';

import { axiosInstance, axiosInstanceToken } from './config';

const postPassword = async ({ hostId, password }: any) => {
  const { data }: AxiosResponse<ApiTokenData> = await axiosInstance({
    method: 'POST',
    url: `api/hosts/${hostId}/enter`,
    data: {
      password,
    },
  });

  return data;
};

const getSpaces = async () => {
  const { data }: AxiosResponse<ApiSpacesData> = await axiosInstanceToken({
    method: 'GET',
    url: `api/spaces`,
  });

  return data;
};

const getJobs = async ({ spaceId }: any) => {
  const { data }: AxiosResponse<ApiJobData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces/${spaceId}/jobs`,
  });

  return data;
};

const getJobActive = async ({ jobId }: any) => {
  const { data }: AxiosResponse<ApiJobActiveData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/active`,
  });

  return data;
};

const postNewTasks = async ({ jobId }: any) => {
  return await axiosInstanceToken({
    method: 'POST',
    url: `/api/jobs/${jobId}/tasks/new`,
  });
};

const getTasks = async ({ jobId }: any) => {
  const { data }: AxiosResponse<ApiTaskData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/tasks`,
  });

  return data;
};

const postCheckTask = async ({ taskId }: any) => {
  return await axiosInstanceToken({
    method: 'POST',
    url: `/api/tasks/${taskId}/flip`,
  });
};

const postJobComplete = async ({ jobId, author }: any) => {
  return await axiosInstanceToken({
    method: 'POST',
    url: `/api/jobs/${jobId}/complete`,
    data: {
      author,
    },
  });
};

const apis = { postPassword, getSpaces, getJobs, getJobActive, postNewTasks, getTasks, postCheckTask, postJobComplete };

export default apis;
