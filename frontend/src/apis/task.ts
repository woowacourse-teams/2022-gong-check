import { AxiosResponse } from 'axios';

import { ID } from '@/types';
import { ApiTaskData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const postNewRunningTasks = async (jobId: ID) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/jobs/${jobId}/runningTasks/new`,
  });
};

const getRunningTasks = async (jobId: ID) => {
  const { data }: AxiosResponse<ApiTaskData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/runningTasks`,
  });

  return data;
};

const getTasks = async (jobId: ID) => {
  const { data }: AxiosResponse<ApiTaskData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/tasks`,
  });

  return data;
};

const postCheckTask = (taskId: ID) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/tasks/${taskId}/flip`,
  });
};

const apiTask = { postCheckTask, getRunningTasks, getTasks, postNewRunningTasks };

export default apiTask;
