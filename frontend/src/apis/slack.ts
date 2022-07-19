import axios, { AxiosResponse } from 'axios';

import { axiosInstanceToken } from './config';

const getSlackUrl = async (jobId: number) => {
  const { data }: AxiosResponse<{ slackUrl: string }> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/slack`,
  });

  return data;
};

const slackApi = { getSlackUrl };

export default slackApi;
