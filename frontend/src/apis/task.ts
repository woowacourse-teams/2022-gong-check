import { AxiosResponse } from 'axios';

import { ID } from '@/types';
import { ApiTaskData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const postNewRunningTasks = async (jobId: ID | undefined) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/jobs/${jobId}/runningTasks/new`,
  });
};

const getRunningTasks = async (jobId: ID | undefined) => {
  const { data }: AxiosResponse<ApiTaskData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/runningTasks`,
  });

  return data;
};

const getTasks = async (jobId: ID | undefined) => {
  const { data }: AxiosResponse<ApiTaskData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/runningTasks`,
  });

  return data;
};

const postCheckTask = ({ taskId }: any) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/tasks/${taskId}/flip`,
  });
};

const apiTask = { postCheckTask, getRunningTasks, getTasks, postNewRunningTasks };

export default apiTask;
