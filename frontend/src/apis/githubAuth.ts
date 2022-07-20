import { AxiosResponse } from 'axios';

import { axiosInstance } from './config';

const getToken = async (code: string | null) => {
  const {
    data,
  }: AxiosResponse<{
    token: string;
    alreadyJoin: boolean;
  }> = await axiosInstance({
    method: 'POST',
    url: '/api/login',
    data: {
      code,
    },
  });

  return data;
};

const apiAuth = { getToken };

export default apiAuth;
