import { axiosInstanceToken } from '../config';
import { AxiosResponse } from 'axios';

import { ApiTaskData } from '@/types/apis';

const postNewTasks = async ({ jobId }: any) => {
  return axiosInstanceToken({
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

const postCheckTask = ({ taskId }: any) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/tasks/${taskId}/flip`,
  });
};

const apis = { postCheckTask, getTasks, postNewTasks };

export default apis;
