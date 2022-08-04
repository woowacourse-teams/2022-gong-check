import { AxiosResponse } from 'axios';

import { ApiEntranceCodedData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const getEntranceCode = async () => {
  const { data }: AxiosResponse<ApiEntranceCodedData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/hosts/entranceCode`,
  });

  return data;
};

const apiHost = { getEntranceCode };

export default apiHost;
