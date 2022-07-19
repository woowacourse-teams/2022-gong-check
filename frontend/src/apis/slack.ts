import { AxiosResponse } from 'axios';

import { axiosInstanceToken } from './config';

type ApiSlackUrlData = {
  slackUrl: string;
};

// slack URL 조회
const getSlackUrl = async ({ jobId }: any) => {
  const { data }: AxiosResponse<ApiSlackUrlData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/slack`,
  });

  return data;
};

// slack URL 수정
const putSlackUrl = async ({ jobId }: any) => {
  return axiosInstanceToken({
    method: 'PUT',
    url: `/api/jobs/${jobId}/slack`,
  });
};

const apiSlack = { getSlackUrl, putSlackUrl };

export default apiSlack;
