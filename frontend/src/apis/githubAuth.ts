import { AxiosResponse } from 'axios';

import { ApiHostTokenData } from '@/types/apis';

import { axiosInstance } from './config';

const getToken = async (code: string) => {
  const { data }: AxiosResponse<ApiHostTokenData> = await axiosInstance({
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
