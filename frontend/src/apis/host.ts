import { AxiosResponse } from 'axios';

import { ApiHostIdData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const getHostId = async () => {
  const { data }: AxiosResponse<ApiHostIdData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/hosts/me`,
  });

  return data;
};

const apiHost = { getHostId };

export default apiHost;
