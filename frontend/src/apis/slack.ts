import { AxiosResponse } from 'axios';

import { ID } from '@/types';

import { axiosInstanceToken } from './config';

type ApiSlackUrlData = {
  slackUrl: string;
};

// slack URL 조회
const getSlackUrl = async (jobId: ID) => {
  const { data }: AxiosResponse<ApiSlackUrlData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/slack`,
  });

  return data;
};

// slack URL 수정
const putSlackUrl = (jobId: ID, slackUrl: string) => {
  return axiosInstanceToken({
    method: 'PUT',
    url: `/api/jobs/${jobId}/slack`,
    data: { slackUrl },
  });
};

const apiSlack = { getSlackUrl, putSlackUrl };

export default apiSlack;
